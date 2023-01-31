import java.sql.*;

public class JDBC_Deneme {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        /*
        create table pupils(
        nu char(4),
        isim varchar(25),
        sehir varchar(100)
         );
         */
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sys?serverTimezone=UTC", "root", "1234");
        Statement st = con.createStatement();

        if (!st.execute("drop table pupils")) {
            System.out.println("Pupils table silindi");
        } else {
            System.out.println("silinemedi");
        }
        /*=======================================================================
          ORNEK2: pupils adinda bir tablo olusturunuz

	    ========================================================================*/

        String createPupils = "create table pupils" +
                "(nu int, " +
                "isim varchar(25), " +
                "sehir varchar(15))";

        if (!st.execute(createPupils)) {
            System.out.println("Table olusturuldu");
        }
         /*=======================================================================
		  ORNEK3: isciler tablosuna yeni bir kayit (80, 'ARGE', 4000)
		  ekleyelim.
		========================================================================*/
        int a = st.executeUpdate("insert into pupils values (4,'Ali','Ankara')");
        System.out.println(a + " satir eklendi");

        /*=======================================================================
	      ORNEK4: isciler tablosuna birden fazla yeni kayıt ekleyelim.
	        INSERT INTO isciler VALUES(70, 'HR', 5000)
            INSERT INTO isciler VALUES(60, 'LAB', 3000)
            INSERT INTO isciler VALUES(50, 'ARGE', 4000)
	     ========================================================================*/
        String[] valuesler = {"insert into pupils values (14,'Karen','Kirsehir')",
                "insert into pupils values (8,'Nalan','Bursa')",
                "insert into pupils values (9,'Kemal','Eskisehir')"};
        int count = 0;
        for (String each : valuesler
        ) {
            count += st.executeUpdate(each);
        }
        System.out.println(count + " adet row eklendi");

        String[] valuesler1 = {"insert into pupils values (5,'Sali','Bursa')",
                "insert into pupils values (18,'Erman','Erzurum')",
                "insert into pupils values (19,'Jale','Kars')"};
        for (String each : valuesler1
        ) {
            st.addBatch(each);
        }
        st.executeBatch();
        //table yazdir
        ResultSet pupi = st.executeQuery("select * from pupils");
        while (pupi.next()) {
            System.out.println(pupi.getInt(1) + " " + pupi.getString(2) + " " + pupi.getString(3));
        }
        /*=======================================================================
		  ORNEK 5: Bursa da ki pupilslerin id lerini iki arttir
		========================================================================*/
        st.executeUpdate("update pupils set nu=nu+2 where sehir='Bursa'");
      //table yazdir
        ResultSet pupi1 = st.executeQuery("select * from pupils");
        while (pupi1.next()) {
            System.out.println(pupi1.getInt(1) + " " + pupi1.getString(2) + " " + pupi1.getString(3));

        }
         /*=======================================================================
 ORNEK8: Isciler tablosundan birimi 'ARGE' olan iscileri siliniz.
========================================================================*/
        int s=st.executeUpdate("delete from pupils where sehir in('Kars','Kirsehir')");
        System.out.println(s+" tane row etkilendi");
        ResultSet pupi2 = st.executeQuery("select * from pupils");
        while (pupi2.next()) {
            System.out.println(pupi2.getInt(1) + " " + pupi2.getString(2) + " " + pupi2.getString(3));

        }
    }
}