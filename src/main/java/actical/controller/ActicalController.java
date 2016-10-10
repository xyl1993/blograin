package actical.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import summer.base.util.CommonFun;
import summer.base.util.ConstDefine;
import summer.base.util.DateEditor;
import actical.model.ActicalModel;
import actical.model.InfoModel;
import actical.model.PlModel;
import actical.model.SearchModel;
import actical.service.ActicalService;


@Controller
@RequestMapping("actical")
public class ActicalController {
	
	@Autowired private ActicalService acticalService;
	
	/**
	 * 发布文章
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/save",method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object>save(@RequestBody ActicalModel model, HttpServletRequest request,HttpServletResponse response){
		return acticalService.save(model,request.getHeader("token"));
	}
	
	/**
	 * 获取文章列表。不需要登录
	 * @param pageIndex
	 * @param pageSize
	 * @param searchModel
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getActList/paging/{pageIndex}/{pageSize}",method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object>getActList(@PathVariable int pageIndex,@PathVariable int pageSize,
			@RequestBody SearchModel searchModel,
			HttpServletRequest request,HttpServletResponse response){
		// 取得request中的数据对象
		return acticalService.getActList(pageIndex,pageSize,searchModel);
	}
	
	/**
	 * 获取我发布的文章。需要登录
	 * @param pageIndex
	 * @param pageSize
	 * @param searchModel
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getMySendActList/paging/{pageIndex}/{pageSize}",method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object>getMySendActList(@PathVariable int pageIndex,@PathVariable int pageSize,
			@RequestBody SearchModel searchModel,
			HttpServletRequest request,HttpServletResponse response){
		// 取得request中的数据对象
		return acticalService.getMySendActList(pageIndex,pageSize,searchModel,request.getHeader("token"));
	}
	
	/**
	 * 获取热门文章
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getHotActList",method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object>getHotActList(HttpServletRequest request,HttpServletResponse response){
		return acticalService.getHotActList();
	}
	
	/**
	 * 获取最新文章
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getNewActList",method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object>getNewActList(HttpServletRequest request,HttpServletResponse response){
		return acticalService.getNewActList();
	}
	
	/**
	 * 获取我参与的文章
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getMyActList/paging/{pageIndex}/{pageSize}",method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object>getMyActList(@PathVariable int pageIndex,@PathVariable int pageSize,
			@RequestBody SearchModel searchModel,
			HttpServletRequest request,HttpServletResponse response){
		return acticalService.getMyActList(pageIndex,pageSize,searchModel,request.getHeader("token"));
	}
	
	/**
	 * 获取文章详情
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getActDetail",method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object>getActDetail(@RequestBody ActicalModel model, HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("id", model.getId());
		return acticalService.getActDetail(paramsMap);
	}
	
	/**
	 * 发布评论
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/saveReplay",method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object>saveReplay(@RequestBody PlModel model, HttpServletRequest request,HttpServletResponse response){
		return acticalService.saveReplay(model,request.getHeader("token"));
	}
	
	/**
	 * 获取文章的评论
	 * @param pageIndex
	 * @param pageSize
	 * @param invitation_id
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getplList/paging/{pageIndex}/{pageSize}/{invitation_id}",method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object>getplList(@PathVariable int pageIndex,@PathVariable int pageSize,@PathVariable int invitation_id, 
			HttpServletRequest request,HttpServletResponse response){
		return acticalService.getplList(pageIndex,pageSize,invitation_id);
	}
	
	/**
	 * 删除文章
	 * @param pageIndex
	 * @param pageSize
	 * @param invitation_id
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/delAct/{invitation_id}",method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object>delAct(@PathVariable int invitation_id, 
			HttpServletRequest request,HttpServletResponse response){
		return acticalService.delAct(invitation_id);
	}
	
	/**
	 * 删除评论
	 * @param pageIndex
	 * @param pageSize
	 * @param invitation_id
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/delReplay/{replayId}",method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object>delReplay(@PathVariable int replayId, 
			HttpServletRequest request,HttpServletResponse response){
		return acticalService.delReplay(replayId);
	}
	
	/**
	 * 获取个人消息
	 * @param pageIndex
	 * @param pageSize
	 * @param invitation_id
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getInfoList/paging/{pageIndex}/{pageSize}",method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object>getInfoList(@PathVariable int pageIndex,@PathVariable int pageSize,@RequestBody InfoModel infoModel, 
			HttpServletRequest request,HttpServletResponse response){
		return acticalService.getInfoList(pageIndex,pageSize,infoModel,request.getHeader("token"));
	}

	/**
	 * 跟新消息为已读
	 * @param id
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/readInfo",method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object>readInfo(int id,HttpServletRequest request,HttpServletResponse response){
		return acticalService.readInfo(id);
	}
	
	/**
	 * 删除消息
	 * @param id
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/delInfo",method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object>delInfo(int id,HttpServletRequest request,HttpServletResponse response){
		return acticalService.delInfo(id);
	}
	
	/**
	 * 
	 * @Title: uploadFile
	 * @Description: TODO(上传文件)
	 * @param @param positionId
	 * @param @return
	 * @return Map<String,Object> 返回类型
	 * @throws
	 */
	@RequestMapping(value = "/uploadFile",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> uploadFile(MultipartFile file,HttpServletRequest request) {

		Map<String, Object> map = new HashMap<String, Object>();
		
	      MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
	        //这里要进行判断，即使是空值，没有上传内容，file都是有值（空值）的，而文件流大小要大于0才是有上传的东西
	        if (file.getSize() > 0){
	          //获得上传文件原始名
	          String imagename = file.getOriginalFilename();
	          //算出后缀名
	          String ext = imagename.substring(imagename.lastIndexOf(".")) ;
	          //对文件类型进行判断，这个操作也可以在前台进行处理，在前台进行处理比较好，前后台都进行处理最稳妥
	          List<String> fileTypes = new ArrayList<String>();
	          fileTypes.add(".bmp");
	          fileTypes.add(".jpg");
	          fileTypes.add(".jpeg");
	          fileTypes.add(".png");
	          fileTypes.add(".gif");
	          //是再进行处理
	          if (fileTypes.contains(ext.toLowerCase())){
	            //文件名为：唯一的工号 + avatar + 系统时间 + 后缀
	            String fileName = System.currentTimeMillis() + ext ;
	            //文件夹;String imagepath = request.getRealPath("/upload");过时的方法，用request.getSession().getServletContext()代替
	            String avatarFolder = request.getSession().getServletContext().getRealPath("/uploadFile");
	            File localFile = new File(avatarFolder,fileName);
	            try {
	              file.transferTo(localFile);
	              map.put(ConstDefine.CONST_SUCCESS_KEY,true);
	              map.put(ConstDefine.CONST_DATA_KEY,request.getContextPath() + "//uploadFile"+"//"+fileName);
	            } catch (IOException e) {
            	  e.printStackTrace();
    			  map.put(ConstDefine.CONST_SUCCESS_KEY, false);
    			  map.put(ConstDefine.CONST_MESSAGE_KEY, e.getMessage());
	            }
	          } else {
	        	  map.put(ConstDefine.CONST_SUCCESS_KEY,false);
	        	  map.put(ConstDefine.CONST_MESSAGE_KEY,"文件格式出错！");
	          }
	        }
		return map;
	
	}
	
	/**
	 * 
	 * @Title: uploadFile
	 * @Description: TODO(上传文件)
	 * @param @param positionId
	 * @param @return
	 * @return Map<String,Object> 返回类型
	 * @throws
	 */
	@RequestMapping(value = "/uploadPicFile",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> uploadPicFile(@RequestParam(value = "filepic", required = false) MultipartFile file,HttpServletRequest request) {

		Map<String, Object> map = new HashMap<String, Object>();
		
	      MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
	        //这里要进行判断，即使是空值，没有上传内容，file都是有值（空值）的，而文件流大小要大于0才是有上传的东西
	        if (file.getSize() > 0){
	          //获得上传文件原始名
	          String imagename = file.getOriginalFilename();
	          //算出后缀名
	          String ext = imagename.substring(imagename.lastIndexOf(".")) ;
	          //对文件类型进行判断，这个操作也可以在前台进行处理，在前台进行处理比较好，前后台都进行处理最稳妥
	          List<String> fileTypes = new ArrayList<String>();
	          fileTypes.add(".bmp");
	          fileTypes.add(".jpg");
	          fileTypes.add(".jpeg");
	          fileTypes.add(".png");
	          fileTypes.add(".gif");
	          //是再进行处理
	          if (fileTypes.contains(ext.toLowerCase())){
	            //文件名为：唯一的工号 + avatar + 系统时间 + 后缀
	            String fileName = System.currentTimeMillis() + ext ;
	            //文件夹;String imagepath = request.getRealPath("/upload");过时的方法，用request.getSession().getServletContext()代替
	            String avatarFolder = request.getSession().getServletContext().getRealPath("/uploadFile");
	            File localFile = new File(avatarFolder,fileName);
	            try {
	              file.transferTo(localFile);
	              map.put(ConstDefine.CONST_SUCCESS_KEY,true);
	              map.put(ConstDefine.CONST_DATA_KEY,request.getContextPath() + "//uploadFile"+"//"+fileName);
	              map.put("fileUrl",request.getContextPath() + "//uploadFile"+"//"+fileName);
	              map.put("fileUrlWeb", request.getContextPath() + "//uploadFile"+"//"+fileName);
	            } catch (IOException e) {
            	  e.printStackTrace();
    			  map.put(ConstDefine.CONST_SUCCESS_KEY, false);
    			  map.put(ConstDefine.CONST_MESSAGE_KEY, e.getMessage());
	            }
	          } else {
	        	  map.put(ConstDefine.CONST_SUCCESS_KEY,false);
	        	  map.put(ConstDefine.CONST_MESSAGE_KEY,"文件格式出错！");
	          }
	        }
		return map;
	}
	
	
	/**
	 * initBinder
	 * 
	 * @param request
	 * @param binder
	 * @throws Exception
	 */
	@InitBinder
	protected void initBinder(HttpServletRequest request,
			ServletRequestDataBinder binder) throws Exception {
		// 对于需要转换为Date类型的属性，使用DateEditor进行处理
		binder.registerCustomEditor(Date.class, new DateEditor());
	}
}
