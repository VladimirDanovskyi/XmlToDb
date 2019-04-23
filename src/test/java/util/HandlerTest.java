package util;

import static org.junit.Assert.assertEquals;
import static util.Handler.TRANSACTION_TAG;
import static util.Handler.PLACE_TAG;
import static util.Handler.AMOUNT_TAG;
import static util.Handler.CURRENCY_TAG;
import static util.Handler.CARD_TAG;
import static util.Handler.CLIENT_TAG;
import static util.Handler.FIRST_NAME_TAG;
import static util.Handler.LAST_NAME_TAG;
import static util.Handler.MIDDLE_NAME_TAG;
import static util.Handler.INN_TAG;


import enteties.ClientEntity;
import enteties.TransactionEntity;
import org.hibernate.Session;
import org.junit.Test;

/**
 * @author Vladimir Danovskyi
 * @company UnitedThinkers
 * @since 2019/04/22
 */


public class HandlerTest {

    private static final String EMPTY_STRING = "";


    @Test
    public void startElement() throws Exception {

        Handler handler = new Handler();
        handler.startElement(EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, null);
        assertEquals(handler.getCurrentClient() == null && handler.getCurrentTransaction() == null, true);
        handler.startElement(EMPTY_STRING, EMPTY_STRING, TRANSACTION_TAG, null);
        assertEquals(handler.getCurrentTransaction() != null, true);
        handler.startElement(EMPTY_STRING, EMPTY_STRING, CLIENT_TAG, null);
        assertEquals(handler.getCurrentClient() != null, true);

    }

    @Test
    public void characters() throws Exception {

        Handler handler = new Handler();
        handler.setCurrentTransaction(new TransactionEntity());
        handler.setCurrentClient(new ClientEntity());
        char[] ch = "<".toCharArray();

        handler.characters(ch, 0, 1);
        TransactionEntity transaction = handler.getCurrentTransaction();
        ClientEntity client = handler.getCurrentClient();
        assertEquals(transaction.getPlace() == null && transaction.getAmount() == null
                        && transaction.getCard() == null && transaction.getId() == 0 && transaction.getCurrency() == null
                        && client.getFirstName() == null && client.getLastName() == null && client.getId() == 0
                        && client.getMiddleName() == null, true);

        handler.setCurrentElement(PLACE_TAG);
        ch = "street".toCharArray();
        handler.characters(ch, 0, 6);
        assertEquals(handler.getCurrentTransaction().getPlace(), "street");

        handler.setCurrentElement(AMOUNT_TAG);
        ch = "25.01".toCharArray();
        handler.characters(ch, 0, 5);
        assertEquals(handler.getCurrentTransaction().getAmount(), (Double)25.01);

        handler.setCurrentElement(CURRENCY_TAG);
        ch = "USD".toCharArray();
        handler.characters(ch, 0, 3);
        assertEquals(handler.getCurrentTransaction().getCurrency(), "USD");

        handler.setCurrentElement(CARD_TAG);
        ch = "4111111111111111".toCharArray();
        handler.characters(ch, 0, 16);
        assertEquals(handler.getCurrentTransaction().getCard(), "4111111111111111");

        handler.setCurrentElement(FIRST_NAME_TAG);
        ch = "Ivan".toCharArray();
        handler.characters(ch, 0, 4);
        assertEquals(handler.getCurrentClient().getFirstName(), "Ivan");

        handler.setCurrentElement(LAST_NAME_TAG);
        ch = "Ivanoff".toCharArray();
        handler.characters(ch, 0, 7);
        assertEquals(handler.getCurrentClient().getLastName(), "Ivanoff");

        handler.setCurrentElement(MIDDLE_NAME_TAG);
        ch = "Ivanoff".toCharArray();
        handler.characters(ch, 0, 7);
        assertEquals(handler.getCurrentClient().getMiddleName(), "Ivanoff");

        handler.setCurrentElement(INN_TAG);
        ch = "1234567890".toCharArray();
        handler.characters(ch, 0, 10);
        assertEquals(handler.getCurrentClient().getId(), 1234567890);

    }

    @Test
    public void endElement() throws Exception {

        Handler handler = new Handler();
        ClientEntity client = new ClientEntity();
        client.setFirstName("Ivan");
        client.setLastName("Petroff");
        client.setMiddleName("Petroff");
        client.setId(1234567891);

        TransactionEntity transaction1 = new TransactionEntity();
        transaction1.setPlace("A PLACE 1");
        transaction1.setCurrency("UAH");
        transaction1.setAmount(10.01);
        transaction1.setCard("123456****1234");

        handler.setCurrentClient(client);
        handler.setCurrentTransaction(transaction1);
        handler.startDocument();
        handler.endElement(EMPTY_STRING, EMPTY_STRING, TRANSACTION_TAG);
        handler.endDocument();
        assertEquals(!(handler.getClientEntities().isEmpty()) && handler.getCurrentClient() == null
                && handler.getCurrentTransaction() == null && handler.getCurrentElement() == null, true);


        handler.startDocument();
        Session session = handler.getSession();
        ClientEntity savedClient = session.get(ClientEntity.class, client.getId());

        assertEquals(client.equals(savedClient), true);

        session.delete(savedClient);
        session.getTransaction().commit();
        session.close();
    }

}