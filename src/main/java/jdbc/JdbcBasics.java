package jdbc;

import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by tairovich_jr on 2022-02-24.
 */
public class JdbcBasics {

    public static void main(String[] args) {


        //expected data (might come from excel, external )
        List< Map<String, String> > expected = new ArrayList<>();
        expected.add(  Map.of("product_id", "1", "name", "Foam Dinner Plate", "quantity_in_stock", "70", "unit_price","1.21") );
        expected.add(  Map.of("product_id", "2", "name", "Pork - Bacon,back Peameal", "quantity_in_stock", "49", "unit_price","4.65") );
        expected.add(  Map.of("product_id", "3", "name", "Lettuce - Romaine, Heart", "quantity_in_stock", "38", "unit_price","3.35") );
        expected.add(  Map.of("product_id", "4", "name", "Brocolinni - Gaylan, Chinese", "quantity_in_stock", "90", "unit_price","4.53") );
        expected.add(  Map.of("product_id", "5", "name", "Sauce - Ranch Dressing", "quantity_in_stock", "94", "unit_price","1.63") );
        expected.add(  Map.of("product_id", "6", "name", "Petit Baguette", "quantity_in_stock", "14", "unit_price","2.39") );
        expected.add(  Map.of("product_id", "7", "name", "Sweet Pea Sprouts", "quantity_in_stock", "98", "unit_price","3.29") );
        expected.add(  Map.of("product_id", "8", "name", "Island Oasis - Raspberry", "quantity_in_stock", "26", "unit_price","0.74") );
        expected.add(  Map.of("product_id", "9", "name", "Longan", "quantity_in_stock", "67", "unit_price","2.26") );
        expected.add(  Map.of("product_id", "10", "name", "Broom - Push", "quantity_in_stock", "6", "unit_price","1.09") );


        List<Map<String,String>> actual = new ArrayList<>();
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/sql_inventory", "root","admin114");

            Statement statement = connection.createStatement();

            String query = "SELECT * FROM products";

            ResultSet result = statement.executeQuery(query);

   //         System.out.println();
  //          System.out.printf("%-27s %-35s %1s %15s %n", "product_id", "name", "quantity_in_stock", "unit_price");
            while (result.next()){

                int product_id = result.getInt("product_id");
                String name = result.getString("name");
                int quantity_in_stock = result.getInt("quantity_in_stock");
                double unit_price = result.getDouble("unit_price");

                actual.add(Map.of("product_id", String.valueOf(product_id), "name", name, "quantity_in_stock",
                        String.valueOf(quantity_in_stock), "unit_price", String.valueOf(unit_price)));

       //         System.out.printf("%-20s %-32s %20s %18s %n", product_id, name, quantity_in_stock, unit_price);
            }

            System.out.println("-------actual--------");

            connection.close();
        }catch (SQLException e){
            e.printStackTrace();
        }

        SoftAssert softAssert = new SoftAssert();
        for (int i = 0; i < expected.size(); i++) {

            Map<String, String> expectedMap = expected.get(i);
            Map<String, String> actualMap = actual.get(i);

            String expectedProductId = expectedMap.get("product_id");
            String actualProductId = actualMap.get("product_id");

            softAssert.assertEquals(actualProductId, expectedProductId);

            String expectedName = expectedMap.get("name");
            String actualName = actualMap.get("name");
            softAssert.assertEquals(actualName, expectedName);

            String expectedQuantityInStock = expectedMap.get("quantity_in_stock");
            String actualQuantity_in_stock = actualMap.get("quantity_in_stock");
            softAssert.assertEquals(actualQuantity_in_stock, expectedQuantityInStock);

            String expectedUnitPrice = expectedMap.get("unit_price");
            String actualUnitPrice = actualMap.get("unit_price");
            softAssert.assertEquals(actualUnitPrice, expectedUnitPrice);

        }

        softAssert.assertAll();

    }
}
