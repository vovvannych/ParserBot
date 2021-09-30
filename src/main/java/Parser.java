
import org.apache.maven.model.Model;
import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Parser {
    private static Document getPage() throws IOException {
        String url = "https://goszakupki.by/tenders/posted";
        Document page = Jsoup.parse(new URL(url).openStream(), "UTF-8", url);
        return page;
    }
    private static Document getNextPage() throws IOException {
        String urlNext = "https://goszakupki.by/tenders/posted?page=2";
        Document pageNext = Jsoup.parse(new URL(urlNext).openStream(), "UTF-8", urlNext);
        return pageNext;
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        Document page = getPage();
        Element tableWth = page.select("table[class=table table-hover table-tds--word-break]").first();
//        System.out.println(tableWth); //выбираем всю таблицу
        Elements tableWths = tableWth.select("tr[data-key]"); // выбираем строки из таблицы
        for (Element table:
             tableWths) {
            String data = table.select("td").text(); // в каждой строке получаем только текст из всех столбцов
            Elements url = table.select("a"); // получаем ссылку
            System.out.println(data);
            System.out.println(url);
        }
        List<String> info = new ArrayList<>();
        info.add(((TextNode) (tableWths.get(0).childNodes().get(0)).childNodes().get(0)).text());
        info.add(tableWths.get(0).childNodes().get(1).childNodes().get(3).childNodes().get(0).toString());
        info.add(((TextNode) (tableWths.get(0).childNodes().get(1)).childNodes().get(0)).text());
        info.add(((TextNode) (tableWths.get(0).childNodes().get(2)).childNodes().get(0)).text());
        info.add(((TextNode) (tableWths.get(0).childNodes().get(4)).childNodes().get(0)).text());
        info.add(((TextNode) (tableWths.get(0).childNodes().get(5)).childNodes().get(0)).text());
        Model1 model = new Model1();
        model.setNumberPurchase(info.get(0));
        model.setDatePurchase(info.get(4));
        model.setSum(info.get(5));
        model.setNameOrg(info.get(2));
        model.setNamePurchase(info.get(1));
        String date = "";
        System.out.println(date + " ");
        info.forEach(System.out::println);
        System.out.println(model);
    }
}
