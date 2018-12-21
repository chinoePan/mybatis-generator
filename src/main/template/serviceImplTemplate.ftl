package ${packageName};
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ${mapperPackageName}.${className?cap_first}Mapper;
import ${packageName}.${className?cap_first}Service;
import ${modelPackageName}.${className?cap_first};
import com.jay.generator.pagehelper.PageInfo;




@Service("${className}Service")
public class ${className?cap_first}ServiceImpl implements ${className?cap_first}Service{
	
	@Autowired
  private ${className?cap_first}Dao ${className}Dao;
 @Override
    List get${className?cap_first}Page(${className?cap_first}QueryForm queryForm, Pager pager){
		Map paramsMap = new HashMap<>();
		paramsMap.put(Pager.KEY, pager);
		 if (queryForm != null) {
		}
		return ${className}Dao.find${className?cap_first}Page(paramsMap);

	}
	@Override
	public int save${className?cap_first}(${className?cap_first} ${className}){
		  int result = ${className}Dao.insert(${className});
		  return result;
	}
    @Override
	public int  modify${className?cap_first}(${className?cap_first} ${className}){
		  int result = ${className}Dao.updateByPrimaryKey(${className});
		  return result;
	}

}
