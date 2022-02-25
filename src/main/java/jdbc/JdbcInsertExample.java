package jdbc;

import com.github.javafaker.Faker;

import java.sql.*;

/**
 * Created by tairovich_jr on 2022-02-24.
 */
public class JdbcInsertExample {

    public static void main(String[] args) {

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/sql_inventory", "root","admin114");

            Statement statement = connection.createStatement (ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            Faker faker = new Faker();
            for (int i = 20; i <= 100; i++) {

                String query = "INSERT INTO products VALUES ("+i+", '"+ faker.book().title().replaceAll("'","") +"', "+
                        faker.random().nextInt(10,100)+", "+20.50+");";

                statement.execute(query);

                query = "SELECT * FROM products";

                ResultSet resultSet = statement.executeQuery(query);

                //simply jumps to the last row
                resultSet.last();

                int product_id = resultSet.getInt("product_id");
                String name = resultSet.getString("name");
                int quantity_in_stock = resultSet.getInt("quantity_in_stock");
                double unit_price = resultSet.getDouble("unit_price");

                System.out.println(product_id + " " + name + " " + quantity_in_stock + " " + unit_price);

            }

            connection.close();
        }catch (SQLException e){
            e.printStackTrace();
        }

    }
}
