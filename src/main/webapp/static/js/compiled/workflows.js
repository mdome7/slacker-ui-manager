var currentWorkflow = null;

function showWorkflow(workflowPathUnderscored) {
  console.log('Showing workflow ' + workflowPathUnderscored);
  $.get('/workflows/' + workflowPathUnderscored, showWorkflowDetails);
}

function showWorkflowDetails(workflow) {
  console.log("Workflow details retrieved");
  console.log(workflow);

  currentWorkflow = workflow;

  // TODO: use Angular
  $('#wf-name').text(workflow.name);
  $('.wf-path').text(workflow.path);
  $('#wf-description').text(workflow.description);
  $('#wf-action-1').text(workflow.actions[0].className);
  $('#request-args').val('');
  $('#wf-details').show();
  $('#wf-details').removeClass('hidden');
}

function submitRequest() {
  execute(currentWorkflow.path, $('#request-args').val())
}

function execute(path, args) {
  var request = (path.join(' ') + ' ' + args).replace(' ', '%20');
  console.log('Request: ' + request);
  $.get('/execute?request=' + request, showResponse);
}

function showResponse(response) {
  console.log('Response:' + response)
  $('#response').text(response);
  $('#response-details').removeClass('hidden');
  $('#response-details').show();
}
