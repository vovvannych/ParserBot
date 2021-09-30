import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Connection;
import org.jsoup.helper.HttpConnection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Parser2 {
    private int countPage = 1;
    public int pages = 0;
    private Document page = null;
    private final String ORIGINAL_SITE = "https://goszakupki.by/tenders/posted?TendersSearch%5Bnum%5D=&TendersSearch%5BiceGiasNum%5D=" +
            "&TendersSearch%5Btext%5D=аккумулятор&TendersSearch%5Bunp%5D=&TendersSearch%5Bcustomer_text%5D=" +
            "&TendersSearch%5BunpParticipant%5D=&TendersSearch%5Bparticipant_text%5D=&TendersSearch%5Bprice_from%5D=" +
            "&TendersSearch%5Bprice_to%5D=&TendersSearch%5Bcreated_from%5D=&TendersSearch%5Bcreated_to%5D=" +
            "&TendersSearch%5Brequest_end_from%5D=&TendersSearch%5Brequest_end_to%5D=&TendersSearch%5Bauction_date_from%5D=" +
            "&TendersSearch%5Bauction_date_to%5D=&TendersSearch%5Bindustry%5D=&TendersSearch%5Btype%5D=" +
            "&TendersSearch%5Bstatus%5D=&TendersSearch%5Bregion%5D=&TendersSearch%5Bappeal%5D=&TendersSearch%5Bfunds%5D=&page=";
    private static final Logger logger = LogManager.getLogger(Parser1.class);


    public String startExecution() throws IOException {

        Document page = getPage(0);
        int totalNumber = pagesCount(page);
        Map<Integer, Model1> model1Map = new HashMap<>();
        Map<Integer, List<String>> aucMap = new HashMap<>();
        for (int i = 0; i < totalNumber; i++) {
            Document tempPage = getPage(i);
            Elements rawFromTable = getRawFromTable(tempPage);
            List<String> infoFromTable = getInfoFromTable(rawFromTable);
            mapFromInfoToModel(model1Map, aucMap);
        }

        return "done";
    }

    private Document getPage(int pageNumber) throws IOException {
        logger.info("entering getPage() method");
        logger.warn("Creating an url for next page");
        logger.warn("now we at page: ");
        logger.warn(pageNumber);
        String tempSiteUrl = ORIGINAL_SITE + pageNumber;
        logger.warn("url created");
        Connection connection = HttpConnection.connect(tempSiteUrl).userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Safari/537.36");
        return page = connection.get();
    }

    private int pagesCount(Document page) {
        logger.info("entering pageCount() method");
        return countPage=Integer.parseInt(String.valueOf(page.select("div[class=summary]").first().childNodes().get(3).childNodes().get(0)).replace(",", ""));
    }

    private Elements getRawFromTable(Document page) {
        logger.info("entering getRawFromTable() method");
        Element tableWth = page.select("table[class=table table-hover table-tds--word-break]").first();
        return tableWth.select("tr[data-key]");
    }

    private List<String> getInfoFromTable (Elements tableWths) {
        logger.info("entering getInfoFromTable method");
        List<String> info = new ArrayList<>();
        info.add(((TextNode) (tableWths.get(0).childNodes().get(0)).childNodes().get(0)).text());
        info.add(tableWths.get(0).childNodes().get(1).childNodes().get(3).childNodes().get(0).toString());
        info.add(((TextNode) (tableWths.get(0).childNodes().get(1)).childNodes().get(0)).text());
        info.add(((TextNode) (tableWths.get(0).childNodes().get(2)).childNodes().get(0)).text());
        info.add(((TextNode) (tableWths.get(0).childNodes().get(4)).childNodes().get(0)).text());
        info.add(((TextNode) (tableWths.get(0).childNodes().get(5)).childNodes().get(0)).text());
        return info;
    }

    private void mapFromInfoToModel(Map<Integer, Model1> model1Map, Map<Integer, List<String>> aucMap) {
        logger.trace("entered into mapFromInfoToModel");
        for (int i = 0; i < aucMap.size(); i++) {
            model1Map.put(i, new Model1());
            model1Map.get(i).setNumberPurchase(aucMap.get(i + 1).get(0));
        }
    }

    private int getCount(Map<Integer, List<String>> aucMap, int count, Elements tableWths, List<String> info) {
        logger.trace("entering getCount method");
        for (Element el :
                tableWths) {
            info.add(((TextNode) (el.childNodes().get(0)).childNodes().get(0)).text());
            aucMap.put(count, new ArrayList<>(info));
            count++;
            info.clear();
        }
        return count;
    }



















    public Parser2() {

    }

    public int getCountPage() {
        return countPage;
    }

    public void setCountPage(int countPage) {
        this.countPage = countPage;
    }
}
