package cn.e3mall.content.service;


import cn.e3mall.common.pojo.DataGridResult;
import cn.e3mall.common.pojo.E3Result;
import cn.e3mall.pojo.TbContent;

public interface ContentService {

	DataGridResult findContentList(long categoryId, int page, int rows);

	E3Result addContent(TbContent tbContent);

	E3Result updateContent(TbContent tbContent);

	E3Result deleteContent(String ids);


}
