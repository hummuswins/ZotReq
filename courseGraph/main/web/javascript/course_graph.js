let container = document.getElementById('mynetwork'); // Container to print the graph

let i = 0;

function addForm(node) {
	$(".form").append("<div class=\"form-check\">\n" +
		"        <input class=\"form-check-input\" type=\"checkbox\" value=\"\" id=\"defaultCheck" + i + "\">\n" +
		"        <label class=\"form-check-label\" for=\"defaultCheck" + i++ + "\">\n" +
		"            " + node.id +
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

	// create a network
	network = new vis.Network(container, data, options);
	network.on('click', function (params) {
		console.log(JSON.stringify(params));

	});
}

// Update color if courses are already taken
$(".form").change(function () {
	let str = "";
	$(".form input:checked").each(function () {
		let res = $("label[for=\'" + $(this)[0].id + "\']").text().replace(/^\s+|\s+$/g, '');
		nodes.update({
			id: res,
			color: {
				border: '#2B7CE9',
				background: '#97C2FC',
			}
		});
	});
	$(".form input:not(:checked)").each(function () {
		console.log("label[for=\'" + $(this)[0].id + "\']");
		let res = $("label[for=\'" + $(this)[0].id + "\']").text().replace(/^\s+|\s+$/g, '');
		str += res + '\n';
		nodes.update({
			id: res,
			color: {
				border: '#F03030',
				background: '#F03030',
			}
		});
	});
	console.log(str);
});