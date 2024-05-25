package modle;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data  // reprecent getters,setters and toString methods

public class CustomerModle {

    private String name;
    private String nic;
    private String addres;
    private int telNo;
}
