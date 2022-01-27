// Sets all boot versions
// Usage: node ./setbootversion.js BOOT_VERSION JAVA_VERSION
//

var path = require('path')
var fs = require('fs')

function recFindPom(base,files,result)
{
    files = files || fs.readdirSync(base)
    result = result || []

    files.forEach(
        function (file) {
            var newbase = path.join(base,file)
            if ( fs.statSync(newbase).isDirectory() )
            {
                result = recFindPom(newbase,fs.readdirSync(newbase),result)
            }
            else
            {
                if ( file === 'pom.xml' && !newbase.includes('target') )
                {
                    result.push(newbase)
                }
            }
        }
    )
    return result
}

pom_file_list = recFindPom('.');

var newVersion = process.argv[2];
var newJavaVersion = process.argv[3];

for(let pom of pom_file_list) {
  var text = fs.readFileSync(pom).toString('utf-8');
  var textByLine = text.split("\n");

  var inParent = false;
  var pomContents = "";

  for(let line of textByLine) {
    if (line.includes('<parent>')) {
      inParent = true;
    }
    if (line.includes('taco-cloud-parent')) {
      inParent = false;
    }
    if (line.includes('<version>') && inParent) {
      line = line.replace(/version>.*<\/version/, 'version>' + newVersion + '</version');
      inParent = false;
    }
    if (line.includes('</version>')) {
      inParent = false;
    }
    if (line.includes('<java.version>')) {
      line = line.replace(/java\.version>.*<\/java\.version/, 'java\.version>' + newJavaVersion + '</java\.version');
    }
    pomContents += line + "\n";
  }

  fs.writeFile(pom, pomContents, (err) => {
    // In case of a error throw err.
    if (err) throw err;
  }) ;
}
