package ua.kiev.unicyb.diploma.domain.entity.configuration;

import lombok.Setter;import lombok.Getter;

import javax.persistence.*;

@Getter@Setter
@Entity
@Table(name = "HASH_TAG")
public class HashTag {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="hash_tag_gen")
    @SequenceGenerator(name="hash_tag_gen", sequenceName="HASH_TAG_SEQ", allocationSize=1)
    private Long hashTagId;

    @Column
    private String value;
}
