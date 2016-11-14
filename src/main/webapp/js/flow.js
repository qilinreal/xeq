/**
 * Created by qi_l on 2016/11/4.
 * 使用my.js
 */

/**
 * 流程类
 * @param {String} bindID 节点ID
 */
function Flow(bindID, xmlStr, editable) {
	/*jsondata = {
		title: "流程设计",
		nodes: {
			node_1: {name: "node_1", left: 67, top: 69, type: "start", width: 24, height: 24},
			node_2: {name: "node_2", left: 219, top: 71, type: "task", width: 86, height: 24},
			node_5: {name: "node_5", left: 380, top: 71, type: "fork", width: 86, height: 24}
		},
		lines: {
			line_3: {type: "tb", M: "69", from: "node_1", to: "node_2", name: "", marked: false},
			line_6: {type: "sl", from: "node_2", to: "node_5", name: "", marked: false}
		}
	};*/

	editable = editable==undefined ? true : editable;
    var property = {
        width: 1366,
        height: 768,
        toolBtns: ["task"],
        haveHead: true,
        headBtns: ["save"],//如果haveHead=true，则定义HEAD区的按钮，现在head被放在了侧方
        haveTool: editable,
        haveGroup: false,
        useOperStack: true
    };

    var remark = {
        cursor: "选择指针",
        direct: "转换连线",
        task: "任务结点"
    };

	xmlStr = xmlStr || '<?xml version="1.0" encoding="UTF-8"?><definitions id="Definition" targetNamespace="http://www.jboss.org/drools" typeLanguage="http://www.java.com/javaTypes" expressionLanguage="http://www.mvel.org/2.0" xmlns="http://www.omg.org/spec/BPMN/20100524/MODEL" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://www.omg.org/spec/BPMN/20100524/MODEL BPMN20.xsd" xmlns:g="http://www.jboss.org/drools/flow/gpd" xmlns:bpmndi="http://www.omg.org/spec/BPMN/20100524/DI" xmlns:dc="http://www.omg.org/spec/DD/20100524/DC" xmlns:di="http://www.omg.org/spec/DD/20100524/DI"	xmlns:tns="http://www.jboss.org/drools"><process processType="Private" isExecutable="true" name="新建流程"><startEvent id="_1"></startEvent><endEvent id="_3"></endEvent></process><bpmndi:BPMNDiagram><bpmndi:BPMNPlane bpmnElement="com.sample.bpmn.hello2"></bpmndi:BPMNPlane></bpmndi:BPMNDiagram></definitions>';
	var xmlDoc = new DOMParser().parseFromString(xmlStr, 'text/xml');

    var instance;
    var jsondata = {};
	var process = xmlDoc.getElementsByTagName('process')[0];
	var BPMNPlane = xmlDoc.getElementsByTagName('BPMNDiagram')[0].getElementsByTagName('BPMNPlane')[0];
	(function() {		// init jsondata
		jsondata.title = process.getAttribute('name');
		var jsonNodes = jsondata.nodes = {};
		jsonNodes._1 = {name: "start", left: 50, top: 50, type: "start", width: 24, height: 24};
		jsonNodes._3 = {name: "end", left: 500, top: 50, type: "end", width: 24, height: 24};
		var nodes = process.getElementsByTagName('scriptTask');
		for(var i=0; i<nodes.length; i++) {
			var toolName = nodes[i].getAttribute('tool-name');
			var id = nodes[i].id;
			jsonNodes[id] = {
				name: toolName,
				type: 'task',
				width: 86,
				height: 24
			};
		}

		var BPMNShapes = BPMNPlane.getElementsByTagName('BPMNShape');
		for(var i=0; i<BPMNShapes.length; i++) {
			var id = BPMNShapes[i].getAttribute('bpmnElement');
			var Bounds = BPMNShapes[i].children[0];
			var x = Bounds.getAttribute('x');
			var y = Bounds.getAttribute('y');
			jsonNodes[id].top = x;
			jsonNodes[id].left = y;
		}

		var sequenceFlows = process.getElementsByTagName('sequenceFlow');
		var jsonLines = jsondata.lines = {};
		for(var i=0; i<sequenceFlows.length; i++) {
			var sourceId = sequenceFlows[i].getAttribute('sourceRef');
			var targetId = sequenceFlows[i].getAttribute('targetRef');
			var M = (jsonNodes[sourceId].top + jsonNodes[sourceId].height / 2 + jsonNodes[targetId].top + jsonNodes[targetId].height / 2) / 2;
			var id = sequenceFlows[i].id;
			jsonLines[id] = {
				type: 'tb',
				M: M,
				from: sourceId,
				to: targetId,
				name: '',	// 无名字
				marked: false
			};
		}
		
		// 取出所有分支合并节点，查找流程中目标是分支的连线，
		// 查找源是分支的连线，修改源为上面获取的值
		var parallelGateways = process.getElementsByTagName('parallelGateway');
		for(var i=0; i<parallelGateways.length; i++) {
			var id = undefined;
			if(parallelGateways[i].gatewayDirection == 'Diverging') {
				for(var j in jsonLines) {
					if(jsonLines[j].to == parallelGateways[i].id) {
						id = jsonLines[j].from;
						break;
					}
				}
				for(var j in jsonLines) {
					if(jsonLines[j].from == parallelGateways[i].id) {
						jsonLines[j].from = id;
					}
				}
			} else {
				for(var j in jsonLines) {
					if(jsonLines[j].from == parallelGateways[i].id) {
						id = jsonLines[j].to;
						break;
					}
				}
				for(var j in jsonLines) {
					if(jsonLines[j].to == parallelGateways[i].id) {
						jsonLines[j].to = id;
					}
				}
			}
		}
	})();

    instance = $.createGooFlow($("#"+bindID), property);
    if(editable) {
    	instance.setNodeRemarks(remark);
    }
    instance.onBtnSaveClick = function () {
        alert('God bless.');
    };
	instance.onItemDel = function (id, type) {
		if(id == '_1' || id == '_3') {
			return false;
		}
	};
	instance.onItemAdd = function(id, type, json) {
		if(type == 'node') {			// 加点
			/*var script = xmlDoc.createElement('scriptTask');
			script.setAttribute('id', id);
			script.setAttribute('name', 'Script');
			script.setAttribute('tool-name', '无工具');
			process.appendChild(script);

			var shape = xmlDoc.createElement('bpmndi:BPMNShape');
			shape.setAttribute('bpmnElement', id);
			var pos = xmlDoc.createElement('dc:Bounds');
			pos.setAttribute('x', json.left);
			pos.setAttribute('y', json.top);
			pos.setAttribute('width', 86);
			pos.setAttribute('height', 24);
			shape.appendChild(pos);
			BPMNPlane.appendChild(shape);*/
		} else {					// 加线
			if(json.from == '_3') {
				return false;
			}
			if(json.to == '_1') {
				return false;
			}

			// 查找已经存在的节点，
			// 如果已经存在from了，则表示其后面跟了一个分支节点
			// 如果已经存在to了，则表示其前面跟了一个聚合节点
			/*var sequenceFlow = xmlDoc.createElement('sequenceFlow');
			sequenceFlow.id = json.from+'-'+json.to;
			sequenceFlow.setAttribute('sourceRef', json.from);
			sequenceFlow.setAttribute('targetRef', json.to);
			process.appendChild(sequenceFlow);*/
		}
		return true;
	};
	instance.onItemMove = function(id, type, left, top) {
		return true;
	};
	instance.onItemRename = function(id,name,type) {
		return true;
	};
	
    instance.$max = 9;
    instance.loadData(jsondata);
    
    var titleBox = document.createElement('input');
    titleBox.id = 'titleBox';
    titleBox.style.position = 'absolute';
    titleBox.style.left = '25px';
    titleBox.style.top = '3px';
    titleBox.style.width = '145px';
    titleBox.style.display = 'none';
    if(document.getElementById(bindID).style.position == undefined) {
    	document.getElementById(bindID).style.position = 'relative';
    };
    document.getElementById(bindID).appendChild(titleBox);
    document.getElementById('titleDom').addEventListener('dblclick', function() {
    	var text = instance.$title;
    	titleBox.value = text;
    	titleBox.style.display = 'inline-block';
    	titleBox.focus();
    	titleBox.select();
    });
    titleBox.addEventListener('blur', function() {
    	if(titleBox.value != "")
    		instance.setTitle(titleBox.value);
    	titleBox.style.display = 'none';
    });
    titleBox.addEventListener('keydown', function(e) {
    	if(e && e.keyCode == 13) {
    		if(titleBox.value != "")
        		instance.setTitle(titleBox.value);
        	titleBox.style.display = 'none';
    	}
    });


	this.getInstance = function() {
		return instance;
	};
	this.asStr = function() {
		// 根据json数据生成xml数据

		// 删除节点
		var scriptTask = process.getElementsByTagName('scriptTask');
		for(var i=0; i<scriptTask.length; i++) { process.removeChild(scriptTask[i]); }
		var parallelGateway = process.getElementsByTagName('parallelGateway');
		for(var i=0; i<parallelGateway.length; i++) { process.removeChild(parallelGateway[i]); }
		var sequenceFlow = process.getElementsByTagName('sequenceFlow');
		for(var i=0; i<sequenceFlow.length; i++) { process.removeChild(sequenceFlow[i]); }
		var BPMNShape = BPMNPlane.getElementsByTagName('BPMNShape');
		for(var i=0; i<BPMNShape.length; i++) { process.removeChild(BPMNShape[i]); }

		// 生成节点，使用原本代码维护的数据
		jsondata.nodes = instance.$nodeData;
		jsondata.lines = instance.$lineData;
		// 遍历整个节点，查看如果有两个节点的源相同，则将这个源记录下来
		// 如果有两个节点的目标相同，则将这个目标记录下来
		// 将其前后方生成分支标志，将其所在位置生成分支
		// 然后将分支和节点放入xml中
		var set = [];
		var fromSet = [];
		for(var i in jsondata.lines) {
			var from = jsondata.lines[i].from;
			if(set.includes(from)) {	// 分支
				fromSet.push(from);
			} else {
				set.push(from);
			}
		}
		set = [];
		var toSet = [];
		for(var i in jsondata.lines) {
			var to = jsondata.lines[i].to;
			if(set.includes(to)) {
				toSet.push(to);
			} else {
				set.push(to);
			}
		}
		for(var i=0; i<fromSet.length; i++) {		// 将所有的分支节点放进node中
			// 对于某个fromSet中的值，创建一个分支节点
			var max = instance.$max++;
			var id = '_jbpm-unique-'+max;
			jsondata.nodes[id] = {
					type: 'fork',		// 分支
					left: 0,
					top: 0,
					width: 86,
					height: 24
			};
			// 替换所有fromSet对应的lines的值
			for(var j in jsondata.lines) {
				if(jsondata.lines[j].from == fromSet[i]) {
					jsondata.lines[j].from = id;
				}
			}
			// 创建和其和值的联系
			max = instance.$max++;
			jsondata.lines[max] = {
					type: 'tb',
					from: fromSet[i],
					to: id,
					marked: false,
					M: 10,
					name: ""
			};
		}
		for(var i=0; i<toSet.length; i++) {			// 将所有的合并节点放进node中
			// 对于某个toSet中的值，创建一个分支节点
			var max = instance.$max++;
			var id = '_jbpm-unique-'+max;
			jsondata.nodes[id] = {
					type: 'join',		// 合并
					left: 0,
					top: 0,
					width: 86,
					height: 24
			};
			// 替换所有fromSet对应的lines的值
			for(var j in jsondata.lines) {
				if(jsondata.lines[j].to == toSet[i]) {
					jsondata.lines[j].to = id;
				}
			}
			// 创建和其和值的联系
			max = instance.$max++;
			jsondata.lines[max] = {
					type: 'tb',
					to: toSet[i],
					from: id,
					marked: false,
					M: 10,
					name: ""
			};
		}

		// 根据node和line的数据生成xml
		for(var i in jsondata.nodes) {
			// 根据类型生成xml节点
			var dom;
			if(jsondata.nodes[i].type == 'task') {
				dom = xmlDoc.createElement('scriptTask');
				dom.id = i;
				dom.setAttribute('name', 'Script');
				dom.setAttribute('tool-name', jsondata.nodes[i].name);
			} else if(jsondata.nodes[i].type == 'fork') {
				dom = xmlDoc.createElement('parallelGateway');
				dom.id = i;
				dom.setAttribute('name', 'Gateway');
				dom.setAttribute('gatewayDirection', 'Diverging');
			} else if(jsondata.nodes[i].type == 'join') {
				dom = xmlDoc.createElement('parallelGateway');
				dom.id = i;
				dom.setAttribute('name', 'Gateway');
				dom.setAttribute('gatewayDirection', 'Converging');
			}
			if(dom)
				process.appendChild(dom);
			
//			<bpmndi:BPMNShape bpmnElement="_1">
//				<dc:Bounds x="0" y="0" width="48" height="48" />
//			</bpmndi:BPMNShape>
			dom = xmlDoc.createElement('bpmndi:BPMNShape');
			dom.setAttribute('bpmnElement', id);
			var dom2 = xmlDoc.createElement('dc:Bounds');
			dom2.setAttribute('x', jsondata.nodes[i].left);
			dom2.setAttribute('y', jsondata.nodes[i].top);
			dom2.setAttribute('width', jsondata.nodes[i].width);
			dom2.setAttribute('height', jsondata.nodes[i].height);
			dom.appendChild(dom2);
			BPMNPlane.appendChild(dom);
		}

		for(var i in jsondata.lines) {
			var dom = xmlDoc.createElement('sequenceFlow');
			dom.id = i;
			dom.setAttribute('sourceRef', jsondata.lines[i].from);
			dom.setAttribute('targetRef', jsondata.lines[i].to);
			process.appendChild(dom);
		}

		var s = new XMLSerializer();
		return s.serializeToString(xmlDoc);
	};


	function format() {
		var ret = this;
		for(var i=0; i<arguments.length; i++) {
			ret = ret.replace(new RegExp("\\{" + i + "\\}", "g"), arguments[i]);
		}
		return ret;
	}
}
