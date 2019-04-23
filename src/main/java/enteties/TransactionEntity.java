package enteties;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author Vladimir Danovskyi
 * @company UnitedThinkers
 * @since 2019/04/22
 */


@Entity
@Table(name = "transaction", schema = "xmltodb")
public class TransactionEntity {
    private long id;
    private String place;
    private Double amount;
    private String currency;
    private String card;
    private ClientEntity client;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID",  nullable = false, insertable = false, updatable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "PLACE")
    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    @Basic
    @Column(name = "AMOUNT")
    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    @Basic
    @Column(name = "CURRENCY")
    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Basic
    @Column(name = "CARD")
    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }




    @ManyToOne
    @JoinColumn(name = "CLIENT_FK")
    public ClientEntity getClient() {
        return this.client;
    }

    public void setClient(ClientEntity client) {
        this.client = client;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TransactionEntity that = (TransactionEntity)o;

        if (id != that.id) {
            return false;
        }
        if (place != null ? !place.equals(that.place) : that.place != null) {
            return false;
        }
        if (amount != null ? !amount.equals(that.amount) : that.amount != null) {
            return false;
        }
        if (currency != null ? !currency.equals(that.currency) : that.currency != null) {
            return false;
        }
        if (card != null ? !card.equals(that.card) : that.card != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int)(id ^ (id >>> 32));
        result = 31 * result + (place != null ? place.hashCode() : 0);
        result = 31 * result + (amount != null ? amount.hashCode() : 0);
        result = 31 * result + (currency != null ? currency.hashCode() : 0);
        result = 31 * result + (card != null ? card.hashCode() : 0);
        return result;
    }

}
