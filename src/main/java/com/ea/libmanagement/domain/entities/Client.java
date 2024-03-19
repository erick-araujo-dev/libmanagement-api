package com.ea.libmanagement.domain.entities;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "client")
@Table(name = "client")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cd_client")
    private int clientId;

    @ManyToOne
    @JoinColumn(name = "cd_adress", referencedColumnName = "cd_adress")
    private Address address;

    @Column(name = "name")
    private String name;

    @Column(name = "phone")
    private String phone;

    @Column(name = "birth_dt")
    private Date birthDate;

    @Column(name = "update_dt")
    private Date updateDate;
}
