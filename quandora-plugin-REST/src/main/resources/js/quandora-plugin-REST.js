AJS.toInit(function() {
   // it will return base domain name only. e.g. yahoo.co.in
  function GetBaseUrl() {
      try {
          var url = location.href;  

          var start = url.indexOf('//');
          if (start < 0)
              start = 0 
          else 
              start = start + 2;  

          var end = url.indexOf('/', start);
          if (end < 0) end = url.length - start;  

          var baseURL = url.substring(0, end) +"/" +url.substring(end+1,url.indexOf('/', end+1));
          return baseURL;
      }
      catch (arg) {
          return null;
      }
  }
     
  function populateForm() {
    AJS.$.ajax({
      url: GetBaseUrl() + "/rest/quandora-rest/1.0/admin-resources",
      dataType: "json",
      success: function(config) {
        AJS.$("#domain").attr("value", config.domain);
        AJS.$("#loginQuandoraREST").attr("value", config.login);
        AJS.$("#pass").attr("value", config.pass);
      }
    });
  }
  
  function updateConfig() {
    AJS.$.ajax({
      url: GetBaseUrl() + "/rest/quandora-rest/1.0/admin-resources",
      type: "PUT",
      contentType: "application/json",
      data: '{ "domain": "' + AJS.$("#domain").attr("value") + '", "login": ' + '"' +  AJS.$("#loginQuandoraREST").attr("value")  + '", "pass": ' + '"' +  AJS.$("#pass").attr("value")+ '" }',
      processData: false,
      success: function(){
        AJS.messages.success({
          title:"Successfully Saved",
          fadeout: true,
          delay: 2000,
          duration: 10000,
          body: ""
        });
      },
      error: function (xhr, ajaxOptions, thrownError) {
        console.error(xhr.status);
        console.error(thrownError);
      }
    });
  }

  populateForm();

  AJS.$("#admin").submit(function(e) {
      e.preventDefault();
      updateConfig();
  });
});

