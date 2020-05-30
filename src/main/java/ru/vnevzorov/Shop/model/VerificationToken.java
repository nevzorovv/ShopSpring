package ru.vnevzorov.Shop.model;

import ru.vnevzorov.Shop.model.user.AbstractUser;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

@Entity
public class VerificationToken {
    private static final int EXPIRATION = 60 * 24;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "veriftoken_gen_seq")
    @SequenceGenerator(name = "veriftoken_gen_seq", initialValue = 1, allocationSize = 1, sequenceName = "veriftoken_seq")
    private Long id;

    private String token;

    @OneToOne(targetEntity = AbstractUser.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "id")
    private AbstractUser abstractUser;

    private LocalDate expiryDate;

    public VerificationToken() {
    }

    public VerificationToken(String token, AbstractUser abstractUser) {
        this.token = token;
        this.abstractUser = abstractUser;
        this.expiryDate = LocalDate.now().plusDays(1);
    }

    public VerificationToken(String token, AbstractUser abstractUser, LocalDate expiryDate) {
        this.token = token;
        this.abstractUser = abstractUser;
        this.expiryDate = expiryDate;
    }

    @Override
    public String toString() {
        return "VerificationToken{" +
                "id=" + id +
                ", token='" + token + '\'' +
                ", abstractUser=" + abstractUser +
                ", expiryDate=" + expiryDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VerificationToken that = (VerificationToken) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(token, that.token) &&
                Objects.equals(abstractUser, that.abstractUser) &&
                Objects.equals(expiryDate, that.expiryDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, token, abstractUser, expiryDate);
    }

    private Date calculateExpiryDate(int expiryTimeInMinutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Timestamp(calendar.getTime().getTime()));
        calendar.add(Calendar.MINUTE, expiryTimeInMinutes);

        return new Date(calendar.getTime().getTime());
    }

    public static int getEXPIRATION() {
        return EXPIRATION;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public AbstractUser getAbstractUser() {
        return abstractUser;
    }

    public void setAbstractUser(AbstractUser abstractUser) {
        this.abstractUser = abstractUser;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }
}
