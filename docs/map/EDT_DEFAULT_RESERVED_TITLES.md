# Reserved Titles

This document contains reserved keywords for *edt* files of the program, either definition or build-in types.  
These titles will be loaded by the default map loader. All the other titles will be loaded and processed by other scripts.
  
## Building

|Keywords|Remarks|
|:-:|:-:|
|hq|基地|
|goldmine|金矿|
|researcher|̫太阳能收集器|
|advanced_researcher|高级太阳能收集器|
|radar|空军基地|
|windmill|发电厂|
|factory_1|轻型工厂|
|factory_2|中型工厂|
|factory_3|重型工厂|
|factory_4|超重型工厂|
|factory_5|极型工厂|
|factory_robot|机器人工厂|
|machine_shop|车间|
|dk_fleet|屠龙舰队整修区|
|turret_1|防护大炮|
|turret_2|防空设施|
|turret_3|离子炮|
|turret_4|莱德风暴|
|turret_5|集束炮塔|
|turret_6|激光炮塔|
  
## Production

|Keywords|Remarks|
|:-:|:-:|
|adventurer|飞碟|
|cannon_1|轻型装甲坦克|
|cannon_2|中型装甲坦克|
|cannon_3|重型装甲坦克|
|cannon_4|超重型装甲坦克|
|cannon_a|T83装甲坦克A|
|cannon_b|T83装甲坦克B|
|flamer_1|轻型喷火坦克|
|flamer_2|中型喷火坦克|
|flamer_3|重型喷火坦克|
|flamer_4|超重型喷火坦克|
|flamer_a|T72喷火坦克A|
|flamer_b|T72喷火坦克B|
|artillery_1|轻型炮兵|
|artillery_2|中型炮兵|
|artillery_3|重型炮兵|
|artillery_4|超重型炮兵|
|artillery_a|T50炮兵A|
|artillery_b|T50炮兵B|
|special_1|轻型特派坦克|
|special_2|中型特派坦克|
|special_3|重型特派坦克|
|special_4|超重型特派坦克|
|special_a|T99特派坦克A|
|special_b|T99特派坦克B|
|antiair_1|轻型防空坦克|
|antiair_2|中型防空坦克|
|antiair_3|重型防空坦克|
|antiair_4|超重型防空坦克|
|antiair_a|T85防空坦克A|
|antiair_b|T85防空坦克B|
|laser_1|激光坦克|
|laser_a|T14激光坦克A|
|laser_b|T14激光坦克B|
|kodiak|科迪亚克|
|daedalus|代达罗斯|
|cougar|美洲狮|
|jaguar|美洲虎|
|avenger|对空复仇者|
|heracles|赫尔克里斯|
|achilles|阿基里斯|
|gaebolg|盖尔伯格|
|gattling|格林机关炮|
|gate|盖特机器人|
|rogon|罗根炸弹|
|laevatein|雷沃丁|
|af_fighter|战斗机|
|af_bomber|轰炸机|
|af_tripler|连环轰炸机|
|af_carry_call|运兵飞船|
|af_meteor|星体炸弹|
|af_orbital_strike|轨道打击|
  
## Upgrade

|Keywords|Remarks|
|:-:|:-:|
|income_1|金钱提炼I|
|income_2|金钱提炼II|
|income_3|金钱提炼III|
|income_4|金钱提炼IV|
|income_5|金钱提炼V|
|accelerate_1|能量流入I|
|accelerate_2|能量流入II|
|accelerate_3|能量流入III|
|accelerate_4|能量流入IV|
|accelerate_5|能量流入V|
|research_1|研究采集I|
|research_2|研究采集II|
|research_3|研究采集III|
|research_4|研究采集IV|
|research_5|研究采集V|
|advanced_research_1|高级研究采集I|
|advanced_research_2|高级研究采集II|
|advanced_research_3|高级研究采集III|
|advanced_research_4|高级研究采集IV|
|advanced_research_5|高级研究采集V|
|focusing_len|聚焦透镜研究|
|repairing_1|自动修复I|
|repairing_2|自动修复II|
  
## Script Keywords

Keywords in this table are all loaded by the *default* map loader.  
If some are missing, they will be loaded as default value. Extras will be processed to map data pool.  
All fields with **-** cannot be initialized with default value. If they are missing, the map will not be loaded.

|Keywords|Remarks|Default Value|
|:-:|:-:|:-:|
|**[sp]**|定义单人任务信息||
|available|该任务可以在单人模式下游玩|1（是）|
|sp.player|玩家控制的单人任务玩家|1|
|difficulty|该任务最大的难度|0|
||||
|**[mp]**|定义多人任务信息||
|available|该任务可以在多人模式下游玩|1（是）|
|max_players|最大的玩家数。此项包括电脑|2|
||||
|**[sp.~]** / **[mp.~]**|定义单人任务中的第~个玩家的信息||
|camp|这个玩家的阵营|1|
|ai|该玩家所使用的AI|player_control（玩家操控）|
|money|玩家在开始时拥有的金钱|0|
|research|玩家在开始时拥有的研究点数|0|
|advanced_research|玩家在开始时拥有的高级研究点数|0|
|max_money|玩家可以拥有的最大额金钱|1073741824|
|max_research|玩家可以拥有的最大额研究点数|5000|
|max_advanced_research|玩家可以拥有的最大额高级研究点数|1000|
|is_neutral|该玩家是否中立。如果玩家操控设为中立之后虽然可以正常操作但是没有建筑生产（科技有效）而且攻击性单位/建筑不会攻击。|0（否）|
||||
|**[building.~]**|定义一个编号为~的建筑物。在这区块中的信息都会用于定义这个建筑||
|sp|是否在单人游戏中出现|1（是）|
|mp|是否在多人游戏中出现|1（是）|
|id|一个自定义ID，可以用于脚本中|-|
|x|建筑物的X轴位置。是建筑物的哪一格视建筑物而定|0|
|y|建筑物的Y轴位置。是建筑物的哪一格视建筑物而定|0|
|owner|建筑物所属|0（中立）|
|type|建筑物的类型，见上|hq|
|level|建筑物的当前等级|0|
|export.**~**|建筑物的第~项生产/科技|-|
|upgrade.**~**|第~项输出可以被升级多少次|0|
|advanced_upgrade.**~**|第~项输出是否可以被高级升级|0（不能）|
|satellite_defence|是否拥有卫星保护|0（没有）|
|hp|建筑物当前的血量。以0-1的浮点数来定义|1（满血）|
||||
|**[unit.~]**|定义一个编号为~的单位。在这区块中的信息都会用于定义这个单位||
|sp|是否在单人游戏中出现|1（是）|
|mp|是否在多人游戏中出现|1（是）|
|id|一个自定义ID，可以用于脚本中|-|
|x|单位的X轴位置|0|
|y|单位的Y轴位置|0|
|owner|建筑物所属|0（中立）|
|type|单位的类型，见上|adventurer（飞碟）|
|hp|单位的当前血量。若大于单位最大血量则视为最大血量|999999（即一般情况下的最大可能血量，当然如果有单位大于这个数也没辙）|
||||
|**[event.~]**|在地图中注册第~个事件||
|sp|该事件在单人任务中会触发|1（是）|
|mp|该事件在多人对战中会触发|0（否）|
