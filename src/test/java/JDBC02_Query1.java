import java.sql.*;

public class JDBC02_Query1 {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        Class.forName("com.mysql.cj.jdbc.Driver");

        Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/sys?serverTimezone=UTC", "root", "1234");

        Statement st= con.createStatement();
        /*=======================================================================
         ORNEK SORU1: Id'si 1'den buyuk firmalarin ismini ve iletisim_isim'ini isim
         ters sirali yazdirin.
        ========================================================================*/
        /*
        CREATE TABLE firmalar
        (
        id INT,
        isim VARCHAR(20),
        iletisim_isim VARCHAR(20),
        CONSTRAINT firmalar_pk PRIMARY KEY (id, isim)
        );

        INSERT INTO firmalar VALUES
        '1', 'ACB', 'Ayse Can'
        '3', 'KRM', 'Ayse Gulmez'
        '4', 'FSD', 'Veli Gul'

         */
       // ResultSet rst = st.executeQuery("Select isim, iletisim_isim from firmalar where id >1 order by isim desc");

        //String selectquery="select isim, iletisim_isim from firmalar where id>1 order by isim desc";
        String selectquery = "SELECT isim, iletisim_isim " +
                "FROM firmalar " +
                "WHERE id>1 " +
                "ORDER BY isim DESC";//sintaxi cok dikkatli yap bosluklara cok dikkat et

        ResultSet data=st.executeQuery(selectquery);

        while(data.next()){
            System.out.println(data.getString("isim")+" "+data.getString("iletisim_isim"));
        }


        System.out.println("=============================ORNEK 2==========================");
        /*=======================================================================
          ORNEK2: Iletisim isminde 'li' iceren firmalarin id'lerini ve isim'ini
          id sirali yazdirin.
        ========================================================================*/
       String selectQuery= "select id,isim from firmalar where iletisim_isim like '%li%' order by id";

       ResultSet veri=st.executeQuery(selectQuery);

        // while(veri.next()){
        //     System.out.println(veri.getInt("id") + " " +
        //             veri.getString("isim"));
        // }

        while(veri.next()){
            System.out.println(veri.getString(1) + " " +
                    veri.getString(2));
        }

        // NOT1 : Sorgulama icin get ile istenirse sütun (field) ismini yazabilecegimiz gibi sutun index
        // (field olusturulma sirasina gore) yazilabilir.

        // NOT2 : Sorgumuzda SELECT'ten sonra sadece belli fieldlari dondurmesini istiyorsak
        // get ile cagirdigimiz field indexleri sorguda belirttigimiz sirayla ifade etmemiz gerekiyor

        con.close();
        st.close();
        veri.close();
        data.close();
    }
}
