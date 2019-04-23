package util;


import enteties.ClientEntity;
import enteties.TransactionEntity;
import java.util.HashSet;
import java.util.Set;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import org.hibernate.Session;

/**
 * @author Vladimir Danovskyi
 * @company UnitedThinkers
 * @since 2019/04/22
 */


public class Handler extends DefaultHandler {

    public static final String TRANSACTION_TAG = "transaction";
    public static final String PLACE_TAG = "place";
    public static final String AMOUNT_TAG = "amount";
    public static final String CURRENCY_TAG = "currency";
    public static final String CARD_TAG = "card";
    public static final String CLIENT_TAG = "client";
    public static final String FIRST_NAME_TAG = "firstName";
    public static final String LAST_NAME_TAG = "lastName";
    public static final String MIDDLE_NAME_TAG = "middleName";
    public static final String INN_TAG = "inn";
    public static Set<Long> clientEntities = new HashSet();
    public static Session session;

    private String currentElement;
    private TransactionEntity currentTransaction;
    private ClientEntity currentClient;
    private static long count = 0;

    public static Set<Long> getClientEntities() {
        return clientEntities;
    }


    public static Session getSession() {
        return session;
    }


    public String getCurrentElement() {
        return currentElement;
    }

    public void setCurrentElement(String currentElement) {
        this.currentElement = currentElement;
    }

    public TransactionEntity getCurrentTransaction() {
        return currentTransaction;
    }

    public void setCurrentTransaction(TransactionEntity currentTransaction) {
        this.currentTransaction = currentTransaction;
    }

    public ClientEntity getCurrentClient() {
        return currentClient;
    }

    public void setCurrentClient(ClientEntity currentClient) {
        this.currentClient = currentClient;
    }


    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        currentElement = qName;

        switch (currentElement) {
            case TRANSACTION_TAG: {
                currentTransaction = new TransactionEntity();
            }
            break;
            case CLIENT_TAG: {
                currentClient = new ClientEntity();
            }
            break;
            default: {
            }
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String text = new String(ch, start, length);
        if (text.contains("<") || currentElement == null) {
            return;
        }
        switch (currentElement) {
            case PLACE_TAG: {
                currentTransaction.setPlace(text);
            }
            break;
            case AMOUNT_TAG: {
                currentTransaction.setAmount(Double.valueOf(text));
            }
            break;
            case CURRENCY_TAG: {
                currentTransaction.setCurrency(text);
            }
            break;
            case CARD_TAG: {
                currentTransaction.setCard(text);
            }
            break;
            case FIRST_NAME_TAG: {
                currentClient.setFirstName(text);
            }
            break;
            case LAST_NAME_TAG: {
                currentClient.setLastName(text);
            }
            break;
            case MIDDLE_NAME_TAG: {
                currentClient.setMiddleName(text);
            }
            break;
            case INN_TAG: {
                currentClient.setId(Long.valueOf(text));
            }
            break;
            default: {
            }
        }

    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {

        if (qName.equals(TRANSACTION_TAG)) {
            saveTransaction(currentClient, currentTransaction);
            currentClient = null;
            currentTransaction = null;
        }

        currentElement = null;

    }

    @Override
    public void startDocument() {
        session = HibernateSessionFactory.getSessionFactory().openSession();
        session.beginTransaction();
    }

    @Override
    public void endDocument() {
        session.getTransaction().commit();
        session.close();
        System.out.println(count + " transactions was saved");
    }


    private static void saveTransaction(ClientEntity client, TransactionEntity transaction) {

        if (clientEntities.contains(client.getId())) {
            transaction.setClient(client);
            session.save(transaction);
        } else {
            client.addTransaction(transaction);
            clientEntities.add(client.getId());
            session.save(client);
        }
        flushAndClearSession();

    }

    private static void flushAndClearSession() {
        ++count;
        if (count % 50 == 0) {
            session.flush();
            session.clear();
        }
    }


}
