let container = document.getElementById('mynetwork'); // Container to print the graph

let i = 0;

function addForm(node) {
	$(".form").append("<div class=\"form-check\">\n" +
		"        <input class=\"form-check-input\" type=\"checkbox\" value=\"\" id=\"" + i + "\">\n" +
		"        <label class=\"form-check-label\" for=\"defaultCheck" + i++ + "\">\n" +
		"            " + node.label +
		"        </label>\n" +
		"    </div>");
}


// AJAX for GET request for servlet
$.ajax({
	url: 'courseGraph',
	success: [(data) => parseData(data)]
});


let network, nodes;

/**
 * This will parse the DOT notation from /courseGraph and represent that in graph
 * @param graphData
 */
function parseData(graphData) {
	let parsedData = vis.network.convertDot(graphData);

	nodes = new vis.DataSet(parsedData.nodes);
	let data = {
		nodes: nodes,
		edges: parsedData.edges
	};
	nodes.forEach(addForm);

	let options = parsedData.options;

	options.nodes = {
		color: 'red'
	};

	// Still not sure which physic would be best for this
	options.physics = {
		solver: 'forceAtlas2Based'
	};

	options.layout = {
		hierarchical: {
			enabled: true
		}
	};

	// create a network
	network = new vis.Network(container, data, options);
	network.on('click', function (params) {
		$("#eventlog").append(JSON.stringify(params));
		let courseId = params.nodes[0];
		console.log(courseId);
		if (courseId === undefined)
			return;
		nodes.update({
			id: courseId,
			color: {
				border: '#2B7CE9',
				background: '#97C2FC',
			}
		});
		$.ajax({
			type: "POST",
			url: "courseGraph",
			data: {
				id: courseId
			},
			dataType: "json",
			success: function (data) {
				data.forEach(function (node) {
					nodes.update({
						id: node,
						color: {
							border: '#CCCC00',
							background: '#FFFF00',
						}
					});
				});
			}
		});
	});
}

// Update color if courses are already taken
$(".form").change(function () {
	let str = "";
	$(".form input:checked").each(function () {
		let courseId = $(this)[0].id;
		nodes.update({
			id: courseId,
			color: {
				border: '#2B7CE9',
				background: '#97C2FC',
			}
		});
		$.ajax({
			type: "POST",
			url: "courseGraph",
			data: {
				id: courseId
			},
			dataType: "json",
			success: function (data) {
				data.forEach(function (node) {
					nodes.update({
						id: node,
						color: {
							border: '#CCCC00',
							background: '#FFFF00',
						}
					});
				});
			}
		});
	});
	$(".form input:not(:checked)").each(function () {
		nodes.update({
			id: $(this)[0].id,
			color: {
				border: '#F03030',
				background: '#F03030',
			}
		});

	});
	console.log(str);
});