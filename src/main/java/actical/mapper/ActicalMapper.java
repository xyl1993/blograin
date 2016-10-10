package actical.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import actical.model.ActicalModel;
import actical.model.InfoModel;
import actical.model.PlModel;

public interface ActicalMapper {
	public void save(ActicalModel model);
	
	public List<ActicalModel> getActList(Map<String,Object> paramsMap);
	
	public List<ActicalModel> getActDetail(Map<String,Object> paramsMap);
	
	public int getActListCount(Map<String,Object> paramsMap);
	
	public List<PlModel>getplList(@Param("rowNum") int rowNum,@Param("pageSize") int pageSize,@Param("invitation_id") int invitation_id);
	
	public List<ActicalModel> getMyActList(Map<String,Object> paramsMap);
	
	public int register(PlModel model);
	
	public int saveReplay(PlModel model);
	
	public int getReplyCount(int value);
	
	public void delAct(int id);
	
	public void delReplay(int invitation_id);
	
	public void delReplayByReplayId(int id);
	
	public List<ActicalModel> getHotActList();
	
	public List<ActicalModel> getNewActList();
	
	//保存信息表
	public void saveInfo(PlModel model);
	
	public List<InfoModel> getInfoList(Map<String,Object> paramsMap);
	
	public void readInfo(int id);
	
	public void delInfo(int id);
}
