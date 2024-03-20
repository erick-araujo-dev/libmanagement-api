package com.ea.libmanagement.domain.entities;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "rent")
@Table(name = "rent")
public class Rent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cd_rent")
    private int cdRent;

    @ManyToOne
    @JoinColumn(name = "cd_client", referencedColumnName = "cd_client")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "cd_copy", referencedColumnName = "cd_copy")
    private Copy copy;

    @Column(name = "rent_dt")
    private Date rentDt;

    @Column(name = "return_dt")
    private Date returnDt;

    @Column(name = "return_condicion")
    private String returnCondition;

}