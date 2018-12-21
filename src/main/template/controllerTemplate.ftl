package ${packageName};
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import ${packageName}.${className?cap_first}Service;
import ${modelPackageName}.${className?cap_first};
import com.jay.generator.pagehelper.PageInfo;

/**
*
* @author generator.wei
*
*/
@Controller
public class ${className?cap_first}Controller {
	
	@Autowired
	private ${className?cap_first}Service ${className}Service;

	/**
	* 分页查询${className?cap_first}
	* @param Map
	* @param page
	* @return modelMap
	*/
	@RequestMapping("/list.do")
    @Authorize(module = "module_admin", result = ResultType.JSON)
	@ResponseBody
	public Map list(@ModelAttribute("queryForm") ${className?cap_first}QueryForm queryForm, @RequestParam(required = false, value = "page", defaultValue = "1") Integer page){
			Pager pager = new Pager(page);
			List  ${className}List  = ${className}Service.get${className?cap_first}Page(queryForm,pager);
			Map modelMap = BaseResult.buildResult();
			modelMap.put("${className}List", ${className}List);
			modelMap.put(Pager.KEY, pager);
		    return modelMap;
	}

	/**
	* 保存${className?cap_first}
	* @param ${className?cap_first}
	* @return succ
	*/
	@RequestMapping(value="/save.do",method= RequestMethod.POST)
    @ResponseBody
	public Map  save${className?cap_first}(@ModelAttribute(" ${className}")${className?cap_first} ${className}){
		  boolean succ = ${className}Service.save${className?cap_first}(${className});
		  String msg = messageSourceHelper.getMessage(succ ? "${className}.save.succ" : "${className}.save.fail");
		  BaseResult result = BaseResult.buildResult(succ, msg);
		  return result;
	}


}
