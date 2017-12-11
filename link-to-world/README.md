# 项目说明
### 设计原则
- 可扩展,使用RESTful服务,易于水平扩展
- 接口强类型,易于后续维护
- 使用Mongodb、MySQL混合, 适应保险产品的复杂存储

### 基础框架
项目基于[Sited](http://www.sited.io "还没有")开发平台构建。Sited是新兴的开源快速开发平台,
包含大量通用组件, 可显著的提升项目开发效率。

项目中使用的Sited组件: 

- Page组件,cms功能,用于显示关于相关页面（没走数据库）
- Customer组件,包括客户管理功能
- User组件,用户注册登录密码相关
- File组件,文件上传下载,图片裁切等

sited的一个标准组件,包括4个部分:
- *-service-api, RESTful接口声明
- *-service, RESTful接口实现
- *-site, 前台网站支持（主要是模板扩展）
- *-admin, 后台管理界面

###项目结构
- dealer-service-api/dealer-service 分别是分销商接口和实现
- insurance-service-api/insurance-service 保险产品接口和实现
- back-office, 管理后台
- website, 前台网站

###项目部署
目前使用gradle的ssh-plugin,直接部署,后续有服务器支持,可以考虑搭建第三方
CI服务器

###产品数据结构
数据结构分成两类, 基础数据和产品数据。基础数据是预置的不易变动的数据。产品数据关联
基础数据,减少维护过程中产生的人工错误。

####基础数据
>基础数据表名格式为insurance-*

基础数据库可以分为以下三类共15个子表：
- 基础信息库--包括供应商表、保险标的表、保险条款表、职业类别表、国家地区表、国内省市表、事故原因表、赔偿项目表、风险保障表和生存领取表。
- 投保信息库--包括投保单字段表、投保规则与核保规则表、客户告知书表。
- 维护信息库--保全变更申请书表和理赔申请书表。

1. 供应商表（InsuranceVendor）
2. 保险标的表（InsuranceSubject）
3. 保险条款表（InsuranceTerm）
4. 保险条款表（InsuranceOccupation）
5. 国家地区表（InsuranceRegion）
6. 国内省市表（InsuranceProvince/InsuranceCity）
7. 风险保障表（InsuranceRiskProtection）
8. 生存领取表（InsuranceSurvival）
9. 用户告知表（InsuranceInformation）
10. 投保单分组（InsuranceFormGroup/InsuranceFormField）

####产品数据
>产品数据表名格式为product-*






