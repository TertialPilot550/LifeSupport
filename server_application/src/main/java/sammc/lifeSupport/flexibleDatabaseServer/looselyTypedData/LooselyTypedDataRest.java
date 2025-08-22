package sammc.lifeSupport.flexibleDatabaseServer.looselyTypedData;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Rest Controller for the flexible database
 * @author sammc
 */
@RestController
public class LooselyTypedDataRest {
    
    @Autowired
    LooselyTypedDataRepository repo;

    @PostMapping("/data")
    public void create(@RequestBody LooselyTypedData data) {
        repo.save(data);
    }

    @GetMapping("/data/{id}")
    public LooselyTypedData read(@PathVariable("id") int id) {
        return repo.findById(id).orElse(null);
    }

    @PutMapping("/data/{id}")
    public boolean update(@PathVariable("id") int id, @RequestBody LooselyTypedData newData) {
        LooselyTypedData toUpdate = repo.findById(id).orElse(null);
        if (toUpdate == null) return false;     // fail to find
        if (newData.getName() != null) toUpdate.setName(newData.getName());
        if (newData.getType() != null) toUpdate.setType(newData.getType());
        if (newData.getData() != null) toUpdate.setData(newData.getData());
        return true;
    }

    @DeleteMapping("/data/{id}") 
    public void delete(@PathVariable("id") int id) {
        repo.deleteById(id);
    }

    @GetMapping("/data/all")
    public List<LooselyTypedData> list() {
        return repo.findAll();
    }


}
