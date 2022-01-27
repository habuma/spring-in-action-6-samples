package tacos.authorization.users;

import java.util.Collection;
import java.util.Collections;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@Entity
@RequiredArgsConstructor
@NoArgsConstructor(force=true, access=AccessLevel.PRIVATE)
public class User implements UserDetails {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  private Long id;
   
  private final String username;
  private final String password;
  private final String role;
  
  @Override
  @Transient
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return Collections.singletonList(new SimpleGrantedAuthority(role));
  }
  
  @Override
  @Transient
  public boolean isAccountNonExpired() {
    return true;
  }
  
  @Override
  @Transient
  public boolean isAccountNonLocked() {
    return true;
  }
  
  @Override
  @Transient
  public boolean isCredentialsNonExpired() {
    return true;
  }
  
  @Override
  @Transient
  public boolean isEnabled() {
    return true;
  }
  
}
