package sammc.lifeSupport.flexibleDatabaseServer.type;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Add and control the different types of data using types
 * @author sammc
 */
public class TypeRest {

    @Autowired
    TypeRepository repo;

    @PostMapping("/type")
    public void create(@RequestBody Type type) {
        repo.save(type);
    }

    @GetMapping("/type/{id}")
    public Type read(@PathVariable("id") int id) {
        return repo.findById(id).orElse(null);
    }

    @PutMapping("/type/{id}")
    public boolean update(@PathVariable("id") int id, @RequestBody Type newData) {
        Type toUpdate = repo.findById(id).orElse(null);
        if (toUpdate == null) return false;     // fail to find
        if (newData.getName() != null) toUpdate.setName(newData.getName());
        if (newData.getDefinition() != null) toUpdate.setDefinition(newData.getDefinition());
        return true;
    }

    @DeleteMapping("/type/{id}") 
    public void delete(@PathVariable("id") int id) {
        repo.deleteById(id);
    }

    @GetMapping("/type/all")
    public List<Type> list() {
        return repo.findAll();
    }
}
