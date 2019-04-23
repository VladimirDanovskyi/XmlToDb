package enteties;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @author Vladimir Danovskyi
 * @company UnitedThinkers
 * @since 2019/04/22
 */


@Entity
@Table(name = "client", schema = "xmltodb")
public class ClientEntity {
    private long id;
    private String firstName;
    private String lastName;
    private String middleName;

    @Id
    @Column(name = "ID",  nullable = false, updatable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    private Set<TransactionEntity> transactions = new HashSet<TransactionEntity>();

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    public Set<TransactionEntity> getTransactions() {
        return this.transactions;
    }

    public void setTransactions(Set<TransactionEntity> transactions) {
        this.transactions = transactions;
    }

    public void addTransaction(TransactionEntity transaction) {
        transaction.setClient(this);
        getTransactions().add(transaction);
    }


    @Basic
    @Column(name = "FIRST_NAME")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Basic
    @Column(name = "LAST_NAME")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Basic
    @Column(name = "MIDDLE_NAME")
    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ClientEntity that = (ClientEntity)o;

        if (id != that.id) {
            return false;
        }
        if (firstName != null ? !firstName.equals(that.firstName) : that.firstName != null) {
            return false;
        }
        if (lastName != null ? !lastName.equals(that.lastName) : that.lastName != null) {
            return false;
        }
        if (middleName != null ? !middleName.equals(that.middleName) : that.middleName != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int)(id ^ (id >>> 32));
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (middleName != null ? middleName.hashCode() : 0);
        return result;
    }

}
