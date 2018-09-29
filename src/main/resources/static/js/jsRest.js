$(document).ready(function(){

    getAllUsers();

    getAllRoles();

});

//---------button events--------------
$(document).on("click","#addUserRestButton", function () {
    addUserRestToServer();
    //alert("User added!");
    $('.nav-tabs a[href="#userTableRest"]').tab('show');
});

$(document).on("click",".delete.btn.btn-danger", function (){
    deleteUserFromServer(this.id);
});

$(document).on("click",".editModal.btn.btn-primary", function (){
    editUserRestToServer();
    $("#modalEditUserRest").modal('toggle');
});

$(document).on('shown.bs.modal','#modalEditUserRest', function (event) {
    var id = $(event.relatedTarget)[0].id;
    getUserbyID(id);
});



//---------work with data------

//---------get data------
function getAllUsers() {

    var rows = $(".rowRest");
    $.each(rows, function () {
        this.remove();
    });


    var cols = $(".colRest");
    $.each(cols, function () {
        this.remove();
    });

    $.ajax({
        type: "GET",
        dataType: "json",
        url: "/api/users/all",
        success: function(users) {
            $.each( users, function( key, val ) {
                addTableRow(val);
            });
        },
        error: function(msg){
            console.log(msg);
        }
    });
}

function getAllRoles(){
    var roles = $(".roleRestAdd");
    $.each(roles, function () {
        this.remove();
    });

    $.ajax({
        type: "GET",
        dataType: "json",
        url: "/api/users/roles",
        success: function(roles) {
            $.each( roles, function( key, val ) {
                if (val == false){
                    $("#addUserRoles").prepend("<option class='roleRestAdd' value='"+key+"')>"+key+"</option>");
                }
                else{
                    $("#addUserRoles").prepend("<option class='roleRestAdd' selected ='true' value='"+key+"')>"+key+"</option>");
                }
            });
        },
        error: function(msg){
            console.log(msg);
        }
    });
}

function getUserbyID(id) {
    $.when($.ajax({
            type: "GET",
            dataType: "json",
            url: "/api/users/"+id,
            success: function(user) {
                return user;
            },
            error: function(msg){
                console.log(msg);
                return null;
            }
        }),
        $.ajax({
            type: "GET",
            dataType: "json",
            url: "/api/users/roles/"+id,
            success: function(roles) {
                return roles;
            },
            error: function(msg){
                console.log(msg);
                return null;
            }
        })
    ).then(addUserDataForEdit);
}


//---------edit data------
function addUserRestToServer() {

    var rolesObj = $("#addUserRoles option:selected");

    var data = [];
    data = {
        login   : $("#loginRest").val(),
        password: $("#passwordRest").val(),
        name    : $("#nameRest").val(),
        roles   : []
    };



    for (var i = 0; i < rolesObj.length; i++) {
        data.roles.push({"type" : rolesObj[i].text})
    }

    $.ajax({
        type: "POST",
        dataType: "json",
        contentType: "application/json",
        url: "/api/users",
        data: JSON.stringify(data),
        success: function(user) {
            addTableRow(user);
            clearAddUser();
        },
        error: function(msg){
            console.log(msg);
        }
    });
}

function editUserRestToServer() {

    var rolesObj = $("#editUserRoles option:selected");

    var id = $("#edidstatic").val();
    var data = [];
    data = {
        password: $("#edpasswordstatic").val(),
        name    : $("#ednamestatic").val(),
        roles   : []
    };

    for (var i = 0; i < rolesObj.length; i++) {
        data.roles.push({"type" : rolesObj[i].text})
    }

    $.ajax({
        type: "PUT",
        dataType: "json",
        contentType: "application/json",
        url: "/api/users/"+id,
        data: JSON.stringify(data),
        success: function(user) {
            upDateTableRow(user);
        },
        error: function(msg){
            console.log(msg);
        }
    });
}

function deleteUserFromServer(id) {
    $.ajax({
        type: "DELETE",
        url: "/api/users/"+id,
        success: function(data) {
            deleteTableRow(id);
        },
        error: function(msg){
            console.log(msg);
        }
    });
}


//---------work with tags and attributes------
function addUserDataForEdit(usr, roles){

    $("#edidstatic").val(usr[0].id);
    $("#edloginstatic").val(usr[0].login);
    $("#edpasswordstatic").val(usr[0].password);
    $("#ednamestatic").val(usr[0].name);

    var cols = $(".roleRestEdit");
    $.each(cols, function () {
        this.remove();
    });

    $.each(roles[0], function( key, val ) {
        if (val == false){
            $("#editUserRoles").prepend("<option class='roleRestEdit' value='"+key+"')>"+key+"</option>");
        }
        else{
            $("#editUserRoles").prepend("<option class='roleRestEdit' selected ='true' value='"+key+"')>"+key+"</option>");
        }
    });
}

function addTableRow(data){
    $("#restTable tr:last").after($("<tr class='rowRest' id='"+data.id+"'></tr>"))
    $("#restTable tr:last").prepend($("<td class='colRest colRestlogin' id='"+data.id+"'>"+data.login+"</td>"));
    $("#restTable td:last").after($("<td class='colRest colRestName'  id='"+data.id+"'>"+data.name+"</td>"));
    $("#restTable td:last").after($("<td class='colRest' id='"+data.id+"'><a class='edit btn btn-primary' role='button' type='button' value='Submit' data-toggle='modal' data-target='#modalEditUserRest' id="+data.id+">edit</a></td>"));
    $("#restTable td:last").after($("<td class='colRest' id='"+data.id+"'><a class='delete btn btn-danger' role='button' type='button' value='Submit'  id="+data.id+">delete</a></td>"));
}

function upDateTableRow(user) {
    if (user == null){
        return;
    }
    var id = user.id;
    if (user.login != null){
        $(".colRest.colRestlogin[id="+id+"]").text(user.login);
    }
    if (user.name != null){
        $(".colRest.colRestName[id="+id+"]").text(user.name);
    }
}

function deleteTableRow(id) {
    $("tr[id="+id+"]").remove();
}

function clearAddUser() {
    $("#loginRest").val('');
    $("#passwordRest").val('');
    $("#nameRest").val('');
}