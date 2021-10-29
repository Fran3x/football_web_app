package projektPAP.Highscore.Generator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GeneratorRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public String argumentsString(List<String> arguments, String mode){
        String output = "";
        int i = 0;
        while (i < arguments.size()) {
            output = output + arguments.get(i) + " ";
            i++;
            if (mode == "create"){
                output = output + arguments.get(i) + " ";
                i++;
            }
            if (i != arguments.size()){
                output += ", ";
            }
        }
        return output;
    }

    public void createTable(String table_name, List<String> columns){
        String sql = "CREATE TABLE " + table_name + "(";
        sql += argumentsString(columns, "create");
        sql += ")";
        System.out.println(sql);
        jdbcTemplate.update(sql);
    };

    public void dropTable(String table_name){
        String sql = "DROP TABLE " + table_name;
        System.out.println(sql);
        jdbcTemplate.update(sql);
    };

    public void insertRow(String table_name, List<String> columns_names, List<String> columns_values){
        String sql = "INSERT INTO " + table_name + "(";
        sql += argumentsString(columns_names, "insert");
        sql += ") VALUES (";
        sql += argumentsString(columns_values, "insert");
        sql += ")";
        //System.out.println(sql);
        jdbcTemplate.update(sql);
    };

}
