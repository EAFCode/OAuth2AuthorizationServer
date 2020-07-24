package pro.it.serverauthjwtbega.model;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import pro.it.serverauthjwtbega.constants.Genre;
import pro.it.serverauthjwtbega.constants.Role;
import pro.it.serverauthjwtbega.constants.StatusUser;
import pro.it.serverauthjwtbega.converter.SequenceNumberConverter;
import pro.it.serverauthjwtbega.converter.StringConverter;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class User  implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Convert(converter = StringConverter.class)
    private String name;

    private LocalDate birthday;

    @Enumerated(EnumType.ORDINAL)
    private Genre genre;

    @Convert(converter = SequenceNumberConverter.class)
    private String mobile;

    @Column(unique = true)
    @Convert(converter = StringConverter.class)
    private String email;

    @Convert(converter = StringConverter.class)
    private String country;

    @Convert(converter = StringConverter.class)
    private String province;

    @Convert(converter = StringConverter.class)
    private String municipality;

    @Convert(converter = StringConverter.class)
    private String hood;

    @Convert(converter = StringConverter.class)
    private String street;


    @Convert(converter = StringConverter.class)
    private String numberHome;

    @Convert(converter = StringConverter.class)
    private String bi;

    @Convert(converter = StringConverter.class)
    private String nif;

    @Enumerated(EnumType.ORDINAL)
    private StatusUser status;

    private String password;

    @NotNull
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(this.role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.status.compareTo(StatusUser.ACTIVE)==0;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.status.compareTo(StatusUser.SUSPENDEND)!=0;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.status.compareTo(StatusUser.ACTIVE)==0;
    }

    @Override
    public boolean isEnabled() {
        return this.status.compareTo(StatusUser.ACTIVE) == 0;
    }


}
