import java.util.Objects;

public class Model1 {
    //это наш объект, который будет нам присылать(возвращать) значения
    //мы отправляем запрос, API возвращает нам json наполянем нашу модель данными распарсив этот json
    //и отправим обратно куда нужно
    private String nameOrg;
    private String nameProcedure;
    private String namePurchase;
    private String datePurchase;
    private String numberPurchase;
    private String sum;
    private String link;

    public Model1() {
    }

    public String getNameOrg() {
        return nameOrg;
    }

    public void setNameOrg(String nameOrg) {
        this.nameOrg = nameOrg;
    }

    public String getNameProcedure() {
        return nameProcedure;
    }

    public void setNameProcedure(String nameProcedure) {
        this.nameProcedure = nameProcedure;
    }

    public String getNamePurchase() {
        return namePurchase;
    }

    public void setNamePurchase(String namePurchase) {
        this.namePurchase = namePurchase;
    }

    public String getDatePurchase() {
        return datePurchase;
    }

    public void setDatePurchase(String datePurchase) {
        this.datePurchase = datePurchase;
    }

    public String getNumberPurchase() {
        return numberPurchase;
    }

    public void setNumberPurchase(String numberPurchase) {
        this.numberPurchase = numberPurchase;
    }

    public String getSum() {
        return sum;
    }

    public void setSum(String sum) {
        this.sum = sum;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public String toString() {
        return "Model1{" +
                "nameOrg='" + nameOrg + '\'' +
                ", nameProcedure='" + nameProcedure + '\'' +
                ", namePurchase='" + namePurchase + '\'' +
                ", datePurchase='" + datePurchase + '\'' +
                ", numberPurchase='" + numberPurchase + '\'' +
                ", sum='" + sum + '\'' +
                ", link='" + link + '\'' +
                '}';
    }

    public String getInfo() {
        return getNameOrg() + '\n' +
                getNamePurchase() + '\n' +
                getLink() + '\n' +
                getDatePurchase() + '\n' +
                getSum() + '\n' +
                getNumberPurchase() + '\n' +
                getNameProcedure() + '\n';

    }
}
