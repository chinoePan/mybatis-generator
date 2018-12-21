package ${packageName};
import java.util.List;
import java.util.Map;
import ${modelPackageName}.${className?cap_first};
import com.jay.generator.pagehelper.PageInfo;

/**
*
* @author generator.wei
*
*/
public interface ${className?cap_first}Service {
	/**
	* 分页查询
	* @param pageInfo
	* @return
	*/
    List get${className?cap_first}Page(${className?cap_first}QueryForm queryForm, Pager pager);

	/**
	* 新增数据
	* @param ${className}
	* @return
	*/
	int  save${className?cap_first}(${className?cap_first} ${className});


	


}
