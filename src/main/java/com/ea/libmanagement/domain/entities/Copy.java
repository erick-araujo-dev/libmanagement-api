package com.ea.libmanagement.domain.entities;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "copy")
@Table(name = "copy")
public class Copy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cd_copy")
    private int cdCopy;

    @ManyToOne
    @JoinColumn(name = "cd_book", referencedColumnName = "cd_book")
    private Book book;

    @Column(name = "status")
    private String status;

    @Column(name = "condicion")
    private String condition;

    @Column(name = "update_dt")
    private Date updateAt;

    @Column(name = "create_dt")
    private Date createAt;
}