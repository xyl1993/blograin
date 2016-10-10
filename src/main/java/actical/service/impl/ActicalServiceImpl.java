package actical.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
















import login.mapper.LoginMapper;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import summer.base.util.ConstDefine;
import summer.base.util.Pagination;
import summer.core.constant.BusinessError;
import actical.mapper.ActicalMapper;
import actical.model.ActicalModel;
import actical.model.InfoModel;
import actical.model.PlModel;
import actical.model.SearchModel;
import actical.service.ActicalService;

@Service
public class ActicalServiceImpl implements ActicalService {

	@Autowired 
	private ActicalMapper mapper;
	
	@Autowired
	private LoginMapper loginMapper;
	
	protected static Logger logger = Logger.getLogger("UserService");// 异常信息
	public Map<String,Object> save(ActicalModel model,String token) {
		Map<String, Object> respMap = new HashMap<String, Object>();
		try{
			int userId = loginMapper.getUserIdByToken(token);
			model.setCreateTime(new Date());
			model.setUserId(userId);
			mapper.save(model);
			respMap.put(ConstDefine.CONST_SUCCESS_KEY, true);
		}catch(Exception e){
			logger.error(e.getMessage());
			respMap.put(ConstDefine.CONST_SUCCESS_KEY, false);
			respMap.put(ConstDefine.CONST_MESSAGE_KEY, BusinessError.SERVELT_ERROR);
		}
		
		return respMap;
	}
	public Map<String, Object> getActList(int pageIndex,int pageSize,SearchModel searchModel) {
		Map<String,Object> paramsMap = new HashMap<String,Object>();
		paramsMap.put("searchText", searchModel.getSearchText());
		paramsMap.put("userId", searchModel.getUserId());
		Map<String,Object> respMap = new HashMap<String,Object>();
		int rowNum = (pageIndex-1)*pageSize;
		paramsMap.put("rowNum", rowNum);
		paramsMap.put("pageSize", pageSize);
		try{
			List<ActicalModel> dataList = mapper.getActList(paramsMap);
			int count  = mapper.getActListCount(paramsMap);
			int pageCount = (count/pageSize)+(count%pageSize==0?0:1);
			Pagination<ActicalModel> pagination = new Pagination<ActicalModel>(pageIndex, pageSize, pageCount, count, dataList);
			respMap.put(ConstDefine.CONST_SUCCESS_KEY, true);
			respMap.put(ConstDefine.CONST_DATA_KEY, pagination);
		}catch(Exception e){
			logger.error(e.getMessage());
			respMap.put(ConstDefine.CONST_SUCCESS_KEY, false);
			respMap.put(ConstDefine.CONST_MESSAGE_KEY, BusinessError.SERVELT_ERROR);
		}
		
		return respMap;
	}
	public Map<String, Object> getActDetail(Map<String, Object> paramsMap) {
		// TODO Auto-generated method stub
		Map<String,Object> respMap = new HashMap<String,Object>();
		try{
			List<ActicalModel> dataList = mapper.getActDetail(paramsMap);
			respMap.put(ConstDefine.CONST_SUCCESS_KEY, true);
			respMap.put(ConstDefine.CONST_DATA_KEY, dataList);
		}catch(Exception e){
			logger.error(e.getMessage());
			respMap.put(ConstDefine.CONST_SUCCESS_KEY, false);
			respMap.put(ConstDefine.CONST_MESSAGE_KEY, BusinessError.SERVELT_ERROR);
		}
		
		return respMap;
	}
	public Map<String, Object> getplList(int pageIndex,int pageSize,int invitation_id) {
		Map<String,Object> respMap = new HashMap<String,Object>();
		int rowNum = (pageIndex-1)*pageSize;
		try{
			List<PlModel> dataList = mapper.getplList(rowNum,pageSize,invitation_id);
			int count  = mapper.getReplyCount(invitation_id);
			int pageCount = (count/pageSize)+(count%pageSize==0?0:1);
			Pagination<PlModel> pagination = new Pagination<PlModel>(pageIndex, pageSize, pageCount, count, dataList);
			respMap.put(ConstDefine.CONST_SUCCESS_KEY, true);
			respMap.put(ConstDefine.CONST_DATA_KEY, pagination);
		}catch(Exception e){
			logger.error(e.getMessage());
			respMap.put(ConstDefine.CONST_SUCCESS_KEY, false);
			respMap.put(ConstDefine.CONST_MESSAGE_KEY, BusinessError.SERVELT_ERROR);
		}
		
		return respMap;
	}
	public Map<String, Object> saveReplay(PlModel model,String token) {
		Map<String,Object> respMap = new HashMap<String,Object>();
		try{
			int userId = loginMapper.getUserIdByToken(token);
			model.setUserId(userId);
			mapper.saveReplay(model); 
			int repalyIdNow = model.getId();      //保存完之后返回现在评论的id
			System.out.println(repalyIdNow);
			if(model.getReplayUserId()!=0 && model.getReplayUserId() != userId){               //引用人id不是自己
				model.setUserId(model.getReplayUserId());             //ReplayToId插入时为replay_id  Replayid插入时为replayto_id
				model.setReplayToId(repalyIdNow);
				mapper.saveInfo(model);
			}
			if(model.getFpUserId()!=userId){             //发布人不是自己
				model.setUserId(model.getFpUserId());
				model.setReplayToId(repalyIdNow);
				mapper.saveInfo(model);
			}
			
			respMap.put(ConstDefine.CONST_SUCCESS_KEY, true);
		}catch(Exception e){
			logger.error(e.getMessage());
			respMap.put(ConstDefine.CONST_SUCCESS_KEY, false);
			respMap.put(ConstDefine.CONST_MESSAGE_KEY, BusinessError.SERVELT_ERROR);
		}
		return respMap;
	}
	public Map<String, Object> delAct(int id) {
		Map<String,Object> respMap = new HashMap<String,Object>();
		try{
			mapper.delAct(id);
			mapper.delReplay(id);
			respMap.put(ConstDefine.CONST_SUCCESS_KEY, true);
		}catch(Exception e){
			logger.error(e.getMessage());
			respMap.put(ConstDefine.CONST_SUCCESS_KEY, false);
			respMap.put(ConstDefine.CONST_MESSAGE_KEY, BusinessError.SERVELT_ERROR);
		}
		return respMap;
	}
	public Map<String, Object> getHotActList() {
		Map<String,Object> respMap = new HashMap<String,Object>();
		try{
			List<ActicalModel> datalist = mapper.getHotActList();
			respMap.put(ConstDefine.CONST_SUCCESS_KEY, true);
			respMap.put(ConstDefine.CONST_DATA_KEY, datalist);
		}catch(Exception e){
			logger.error(e.getMessage());
			respMap.put(ConstDefine.CONST_SUCCESS_KEY, false);
			respMap.put(ConstDefine.CONST_MESSAGE_KEY, BusinessError.SERVELT_ERROR);
		}
		return respMap;
	}
	public Map<String, Object> getMyActList(int pageIndex, int pageSize,
			SearchModel searchModel,String token) {
		Map<String,Object> paramsMap = new HashMap<String,Object>();
		paramsMap.put("searchText", searchModel.getSearchText());
		Map<String,Object> respMap = new HashMap<String,Object>();
		int rowNum = (pageIndex-1)*pageSize;
		paramsMap.put("rowNum", rowNum);
		paramsMap.put("pageSize", pageSize);
		try{
			paramsMap.put("userId", loginMapper.getUserIdByToken(token));
			List<ActicalModel> dataList = mapper.getMyActList(paramsMap);
			int count  = mapper.getActListCount(paramsMap);
			int pageCount = (count/pageSize)+(count%pageSize==0?0:1);
			Pagination<ActicalModel> pagination = new Pagination<ActicalModel>(pageIndex, pageSize, pageCount, count, dataList);
			respMap.put(ConstDefine.CONST_SUCCESS_KEY, true);
			respMap.put(ConstDefine.CONST_DATA_KEY, pagination);
		}catch(Exception e){
			logger.error(e.getMessage());
			respMap.put(ConstDefine.CONST_SUCCESS_KEY, false);
			respMap.put(ConstDefine.CONST_MESSAGE_KEY, BusinessError.SERVELT_ERROR);
		}
		
		return respMap;
	}
	public Map<String, Object> getMySendActList(int pageIndex, int pageSize,
			SearchModel searchModel, String token) {
		Map<String,Object> paramsMap = new HashMap<String,Object>();
		paramsMap.put("searchText", searchModel.getSearchText());
		Map<String,Object> respMap = new HashMap<String,Object>();
		int rowNum = (pageIndex-1)*pageSize;
		paramsMap.put("rowNum", rowNum);
		paramsMap.put("pageSize", pageSize);
		try{
			paramsMap.put("userId",loginMapper.getUserIdByToken(token));
			List<ActicalModel> dataList = mapper.getActList(paramsMap);
			int count  = mapper.getActListCount(paramsMap);
			int pageCount = (count/pageSize)+(count%pageSize==0?0:1);
			Pagination<ActicalModel> pagination = new Pagination<ActicalModel>(pageIndex, pageSize, pageCount, count, dataList);
			respMap.put(ConstDefine.CONST_SUCCESS_KEY, true);
			respMap.put(ConstDefine.CONST_DATA_KEY, pagination);
		}catch(Exception e){
			logger.error(e.getMessage());
			respMap.put(ConstDefine.CONST_SUCCESS_KEY, false);
			respMap.put(ConstDefine.CONST_MESSAGE_KEY, BusinessError.SERVELT_ERROR);
		}
		
		return respMap;
	}
	public Map<String, Object> delReplay(int replayId) {
		Map<String,Object> respMap = new HashMap<String,Object>();
		try{
			mapper.delReplayByReplayId(replayId);
			respMap.put(ConstDefine.CONST_SUCCESS_KEY, true);
		}catch(Exception e){
			logger.error(e.getMessage());
			respMap.put(ConstDefine.CONST_SUCCESS_KEY, false);
			respMap.put(ConstDefine.CONST_MESSAGE_KEY, BusinessError.SERVELT_ERROR);
		}
		return respMap;
	}
	public Map<String, Object> getInfoList(int pageIndex, int pageSize,
			InfoModel infoModel, String token) {
		Map<String,Object> paramsMap = new HashMap<String,Object>();
		Map<String,Object> respMap = new HashMap<String,Object>();
		int rowNum = (pageIndex-1)*pageSize;
		paramsMap.put("rowNum", rowNum);
		paramsMap.put("pageSize", pageSize);
		try{
			int userId = loginMapper.getUserIdByToken(token);
			paramsMap.put("isRead", infoModel.getIsRead());
			paramsMap.put("userId", userId);
			List<InfoModel> dataList = mapper.getInfoList(paramsMap);
			int count  = mapper.getActListCount(paramsMap);
			int pageCount = (count/pageSize)+(count%pageSize==0?0:1);
			Pagination<InfoModel> pagination = new Pagination<InfoModel>(pageIndex, pageSize, pageCount, count, dataList);
			respMap.put(ConstDefine.CONST_SUCCESS_KEY, true);
			respMap.put(ConstDefine.CONST_DATA_KEY, pagination);
		}catch(Exception e){
			logger.error(e.getMessage());
			respMap.put(ConstDefine.CONST_SUCCESS_KEY, false);
			respMap.put(ConstDefine.CONST_MESSAGE_KEY, BusinessError.SERVELT_ERROR);
		}
		
		return respMap;
	}
	public Map<String, Object> readInfo(int id) {
		Map<String,Object> respMap = new HashMap<String,Object>();
		try{
			mapper.readInfo(id);
			respMap.put(ConstDefine.CONST_SUCCESS_KEY, true);
		}catch(Exception e){
			logger.error(e.getMessage());
			respMap.put(ConstDefine.CONST_SUCCESS_KEY, false);
			respMap.put(ConstDefine.CONST_MESSAGE_KEY, BusinessError.SERVELT_ERROR);
		}
		return respMap;
	}
	public Map<String, Object> delInfo(int id) {
		Map<String,Object> respMap = new HashMap<String,Object>();
		try{
			mapper.delInfo(id);
			respMap.put(ConstDefine.CONST_SUCCESS_KEY, true);
		}catch(Exception e){
			logger.error(e.getMessage());
			respMap.put(ConstDefine.CONST_SUCCESS_KEY, false);
			respMap.put(ConstDefine.CONST_MESSAGE_KEY, BusinessError.SERVELT_ERROR);
		}
		return respMap;
	}
	
	public Map<String, Object> getNewActList() {
		Map<String,Object> respMap = new HashMap<String,Object>();
		try{
			List<ActicalModel> datalist = mapper.getNewActList();
			respMap.put(ConstDefine.CONST_SUCCESS_KEY, true);
			respMap.put(ConstDefine.CONST_DATA_KEY, datalist);
		}catch(Exception e){
			logger.error(e.getMessage());
			respMap.put(ConstDefine.CONST_SUCCESS_KEY, false);
			respMap.put(ConstDefine.CONST_MESSAGE_KEY, BusinessError.SERVELT_ERROR);
		}
		return respMap;
	}

}
