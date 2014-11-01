package classes;


import java.lang.reflect.Type;
 
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
 
public class AnotherSerializationExample {
    public static void main(String[] args) {
         
        //create GSON builder instance and set pretty printing format for better formatting of JSON string
        GsonBuilder gsonBuilder=new GsonBuilder().setPrettyPrinting();
         
        //register EmployeeSerializer for Employee type of object serialization
        gsonBuilder.registerTypeAdapter(EmployeeObject.class, new EmployeeObjectSerializer());
         
        //create Gson instance to take care of object serialization/deserialization process
        Gson gson=gsonBuilder.create();
         
        //create Employee object and populate data
        EmployeeObject employee=new EmployeeObject();
        employee.setName("Ratan");
        employee.setId(24);
         
        Department[] departments=new Department[3];
         
        Department admin=new Department();
        admin.setId(1);
        admin.setName("admin");
         
        Department finance=new Department();
        finance.setId(2);
        finance.setName("finance");
         
        Department hr=new Department();
        hr.setId(3);
        hr.setName("human resources");
 
        departments[0]=admin;
        departments[1]=finance;
        departments[2]=hr;
         
        //set departments array
        employee.setDepartments(departments);
         
         
        //serialize employee object to json string with the help of already registered EmployeeSerilizer instance
        String jsonData=gson.toJson(employee);
         
        System.out.println("Serialized Data ::"+jsonData);
    }
}
 
//POJO to be serialized using Gson Custom Serializer EmployeeSerializer
class EmployeeObject
{
    private String name;
    private int id;
     
    private Department[] departments;
     
    public String getName() {
        return name;
    }
     
    public void setName(String name) {
        this.name = name;
    }
     
    public int getId() {
        return id;
    }
     
    public void setId(int id) {
        this.id = id;
    }
     
    public void setDepartments(Department[] departments) {
        this.departments = departments;
    }
     
    public Department[] getDepartments() {
        return departments;
    }
}
 
class Department
{
    private int id;
    private String name;
     
    public void setId(int id) {
        this.id = id;
    }
     
    public int getId() {
        return id;
    }
     
    public void setName(String name) {
        this.name = name;
    }
     
    public String getName() {
        return name;
    }
     
}
 
class EmployeeObjectSerializer implements JsonSerializer<EmployeeObject>
{
    public JsonElement serialize(EmployeeObject employee, Type typeOfSource, JsonSerializationContext serializationContext) {
         
        JsonObject jsonObject=new JsonObject();
         
        jsonObject.addProperty("name", employee.getName());
        jsonObject.addProperty("id", employee.getId());
         
        JsonElement departments=serializationContext.serialize(employee.getDepartments());
         
        //write the final departments json array to jsonObject
        jsonObject.add("departments", departments);
         
        return jsonObject;
    }
}
