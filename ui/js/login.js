'use strict';

$(function() {
  var $username = $('#username');
  var $password = $('#password');
  var $login = $('#login');

  $login.on('click', function() {
    var postData = {
      username: $username.val(),
      password: $.md5($password.val())
    };

    $.ajax('/api/login/login', {
      type: 'POST',
      data: JSON.stringify(postData),
      contentType: "application/json; charset=utf-8",
      dataType: 'json',
      success: function(data) {
        if (data.success) {
          window.location.href = '/skin.html';
        } else {
          alert(data.message || '登录失败.');
        }
      }
    });
  });
});
