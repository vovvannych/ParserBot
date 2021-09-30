import org.jsoup.Connection;
import org.jsoup.helper.HttpConnection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

import java.sql.*;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class Parser1 {
    private int countPage = 0;
    private final String URLGos = "https://goszakupki.by";
    public int count = 0;
    private static final Logger logger = LogManager.getLogger(Parser1.class);
    public Parser1() {

    }

    public int getCountPage() {
        return countPage;
    }

    public void setCountPage(int countPage) {
        this.countPage = countPage;
    }

    private Document getPage(String url1) throws IOException {
        String url = "https://goszakupki.by/tenders/posted?TendersSearch%5Bnum%5D=&TendersSearch%5BiceGiasNum%5D=" +
                "&TendersSearch%5Btext%5D=аккумулятор&TendersSearch%5Bunp%5D=&TendersSearch%5Bcustomer_text%5D=" +
                "&TendersSearch%5BunpParticipant%5D=&TendersSearch%5Bparticipant_text%5D=&TendersSearch%5Bprice_from%5D=" +
                "&TendersSearch%5Bprice_to%5D=&TendersSearch%5Bcreated_from%5D=&TendersSearch%5Bcreated_to%5D=" +
                "&TendersSearch%5Brequest_end_from%5D=&TendersSearch%5Brequest_end_to%5D=&TendersSearch%5Bauction_date_from%5D=" +
                "&TendersSearch%5Bauction_date_to%5D=&TendersSearch%5Bindustry%5D=&TendersSearch%5Btype%5D=" +
                "&TendersSearch%5Bstatus%5D=&TendersSearch%5Bregion%5D=&TendersSearch%5Bappeal%5D=&TendersSearch%5Bfunds%5D=&page=" + url1;
        Connection connection = HttpConnection.connect(url).userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Safari/537.36");
        return connection.get();
    }
    public Map<Integer, Model1> pagesGenerator() throws IOException, SQLException {
        Map<Integer, Model1> model1Map = new HashMap<>();

        String url = "https://goszakupki.by/tenders/posted?TendersSearch%5Bnum%5D=&TendersSearch%5BiceGiasNum%5D=" +
                "&TendersSearch%5Btext%5D=аккумулятор&TendersSearch%5Bunp%5D=&TendersSearch%5Bcustomer_text%5D=" +
                "&TendersSearch%5BunpParticipant%5D=&TendersSearch%5Bparticipant_text%5D=&TendersSearch%5Bprice_from%5D=" +
                "&TendersSearch%5Bprice_to%5D=&TendersSearch%5Bcreated_from%5D=&TendersSearch%5Bcreated_to%5D=" +
                "&TendersSearch%5Brequest_end_from%5D=&TendersSearch%5Brequest_end_to%5D=&TendersSearch%5Bauction_date_from%5D=" +
                "&TendersSearch%5Bauction_date_to%5D=&TendersSearch%5Bindustry%5D=&TendersSearch%5Btype%5D=&TendersSearch%5Bstatus%5D=" +
                "&TendersSearch%5Bregion%5D=&TendersSearch%5Bappeal%5D=&TendersSearch%5Bfunds%5D=&page=" ;

        Connection connection = HttpConnection.connect(url).userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Safari/537.36");
        Document page1 = connection.get();

        countPage=Integer.parseInt(String.valueOf(page1.select("div[class=summary]").first().childNodes().get(3).childNodes().get(0)).replace(",", ""));

        logger.warn("count page: ");
        logger.warn(countPage);
        for (int i = 2; i < countPage/20; i++) {
            String url_new = url + i;
            System.out.println(url_new.substring(url_new.length()-2));
            Document page11 = getPage(url_new);

            Element tableWth = page11.select("table[class=table table-hover table-tds--word-break]").first();
            Elements tableWths = tableWth.select("tr[data-key]"); // выбираем строки из таблицы

            for (int j = 0; j < tableWths.size(); j++) {
                List<String> info = mappingFromPage(tableWths, tableWths.size());
                logger.warn("mapping");
                if(info.get(0).equals("outdated")) continue;
                var model = modeling(new Model1(), info);
                System.out.println("Model variables: " + model.toString());
                model1Map.put(count, model);
                count++;
                logger.warn("count equals: ");
                logger.warn(count);
            }

//            dbWork(model1Map, i-2);
        }
        for (int j = 0; j < model1Map.size(); j++) {
            System.out.println(model1Map.get(j).getInfo());
        }
        return model1Map;
    }

    private void dbWork(Map<Integer, Model1> model1Map, int i) throws SQLException {
        for (int j = 0; j < model1Map.size(); j++) {
            java.sql.Connection db = connectDB();
            assert db != null;
            insert(createQuery(model1Map, i), db);
            close(db);
        }
    }

    private Model1 modeling(Model1 model, List<String> info) {
        model.setNumberPurchase(info.get(0));
        model.setDatePurchase(info.get(4));
        model.setSum(info.get(5));
        model.setNameOrg(info.get(2));
        model.setNamePurchase(info.get(1));
        model.setNameProcedure(info.get(3));
        model.setLink(info.get(6));
        logger.warn(info);
        logger.warn("Model was created with parameters: ");
        return model;
    }

    private List<String> mappingFromPage(Elements tableWths, int number) {
        var sum = getDataFilter(tableWths);
        logger.warn("Sum: ");
        logger.warn(Arrays.toString(sum));
        List<String> info = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            if (sum[1] >= 9 && sum[2] == 2021) {
                logger.warn("if statement");
                info.add(((TextNode) (tableWths.get(i).childNodes().get(0)).childNodes().get(0)).text());
                info.add(tableWths.get(i).childNodes().get(1).childNodes().get(3).childNodes().get(0).toString());
                info.add(((TextNode) (tableWths.get(i).childNodes().get(1)).childNodes().get(0)).text());
                info.add(((TextNode) (tableWths.get(i).childNodes().get(2)).childNodes().get(0)).text());
                info.add(((TextNode) (tableWths.get(i).childNodes().get(4)).childNodes().get(0)).text());
                info.add(((TextNode) (tableWths.get(i).childNodes().get(5)).childNodes().get(0)).text());
                info.add(URLGos + tableWths.get(i).childNodes().get(1).childNode(3).attr("href")); //link
            } else {
                info.add("outdated");
            }
        }
        return info;
    }

    private int[] getDataFilter(Elements tableWths) {
        logger.warn("getDataFilter");
        var date = ((TextNode) (tableWths.get(0).childNodes().get(4)).childNodes().get(0)).text();
        System.out.println("date = " + date);
        var sumDate = date.split("\\.");
        System.out.println("sumDate = " + Arrays.toString(sumDate));
        var ints = Arrays.stream(sumDate).mapToInt(Integer::parseInt).toArray();
        System.out.println("ints = " + Arrays.toString(ints));
        return ints;
    }

    private java.sql.Connection connectDB() {
        try{
            java.sql.Connection co = DriverManager.getConnection("jdbc:sqlite:full.db");
            Class.forName("org.sqlite.JDBC");
            logger.warn("Connected to DB");
            return co;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    private String createQuery(Map<Integer, Model1> model1Map, int index) {
        Model1 model = model1Map.get(index);
        String query = "INSERT INTO users (numberPurchase, datePurchase, sum, nameOrg, " +
                "namePurchase, nameProcedure, link_url) VALUES ('" + model.getNumberPurchase() +
                "', '" + model.getDatePurchase() + "', '" + model.getSum() + "', '" + model.getNameOrg() +
                "', '" + model.getNamePurchase() + "', '" + model.getNameProcedure() + "')";
        return query;
    }

    private void insert(String query, java.sql.Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeUpdate(query);
        connection.close();
        logger.warn("Rows added");
    }

    private boolean close(java.sql.Connection connection) {
        boolean state = false;
        if(connection != null) {
            try {
                connection.close();
                logger.warn("Connection closed...");
                state = true;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        return state;
    }
}
