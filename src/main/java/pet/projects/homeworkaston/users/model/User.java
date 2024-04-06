package pet.projects.homeworkaston.users.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;


@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Table(name = "USERS")
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    Long id;
    @Column(name = "username", length = 50, unique = true, nullable = false)
    String username;
    @Email(message = "email должен быть корректным")
    @Column(name = "email", length = 100, unique = true, nullable = false)
    String email;
}
