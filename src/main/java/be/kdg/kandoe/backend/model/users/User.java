package be.kdg.kandoe.backend.model.users;

import be.kdg.kandoe.backend.model.users.roles.Role;
import be.kdg.kandoe.backend.model.users.roles.RoleType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents a user of the application.
 * A user has a username, name, surname, email and password
 */
@Entity
@Table(name = "`User`")
@NoArgsConstructor
@Data
@EqualsAndHashCode(exclude = { "roles" })
@ToString(exclude = "roles", doNotUseGetters = true)
public class User implements Serializable, UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    @Column(unique = true, nullable = false)
    private String username;
    
    private String password;
    
    @Column(unique = true, nullable = false)
    private String email;
    
    private String profilePictureUrl;
    
    @Column(length = 100)
    private String name;
    
    @Column(length = 100)
    private String surname;

    private boolean accountNonExpired = true;
    private boolean accountNonLocked = true;
    private boolean enabled = true;
    private boolean credentialsNonExpired = true;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles")
    private List<Role> roles = new ArrayList<>();

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map(r -> r.getAuthorities()).flatMap(a -> a.stream()).collect(Collectors.toList());
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public void addRole(RoleType roleType) {
        roles.add(Role.getRole(roleType));
    }

    public void addRole(RoleType... roleTypes) {
        Arrays.stream(roleTypes).forEach(this::addRole);
    }

    public List<RoleType> getRoleTypes() {
        return roles.stream().map(r -> r.getRoleType()).collect(Collectors.toList());
    }
}
