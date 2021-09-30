import java.util.Objects;

public class Model {
    //это наш объект, который будет нам присылать(возвращать) значения
    //мы отправляем запрос, API возвращает нам json наполянем нашу модель данными распарсив этот json
    //и отправим обратно куда нужно
    private String nameOrg;
    private String nameProcedure;
    private String namePurchase;
    private Integer datePurchase;
    private Integer numberPurchase;
    private Integer sum;

    public Model() {
    }

    public Model(String nameOrg, String nameProcedure,
                 String namePurchase, Integer datePurchase,
                 Integer numberPurchase, Integer sum) {
        this.nameOrg = nameOrg;
        this.nameProcedure = nameProcedure;
        this.namePurchase = namePurchase;
        this.datePurchase = datePurchase;
        this.numberPurchase = numberPurchase;
        this.sum = sum;
    }

    public Integer getSum() {
        return sum;
    }

    public void setSum(Integer sum) {
        this.sum = sum;
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

    public Integer getDatePurchase() {
        return datePurchase;
    }

    public void setDatePurchase(Integer datePurchase) {
        this.datePurchase = datePurchase;
    }

    public Integer getNumberPurchase() {
        return numberPurchase;
    }

    public void setNumberPurchase(Integer numberPurchase) {
        this.numberPurchase = numberPurchase;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Model model = (Model) o;
        return Objects.equals(nameOrg, model.nameOrg) && Objects.equals(nameProcedure, model.nameProcedure) && Objects.equals(namePurchase, model.namePurchase) && Objects.equals(datePurchase, model.datePurchase) && Objects.equals(numberPurchase, model.numberPurchase) && Objects.equals(sum, model.sum);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nameOrg, nameProcedure, namePurchase, datePurchase, numberPurchase, sum);
    }

    @Override
    public String toString() {
        return "Model{" +
                "nameOrg='" + nameOrg + '\'' +
                ", nameProcedure='" + nameProcedure + '\'' +
                ", namePurchase='" + namePurchase + '\'' +
                ", datePurchase=" + datePurchase +
                ", numberPurchase=" + numberPurchase +
                ", sum=" + sum +
                '}';
    }
}
