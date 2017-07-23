
//左侧消息列表（目前支持类型为 个人personal与群组group）
app.controller('SessionListCtrl', ['$scope', '$rootScope', '$stateParams', function ($rootScope, $scope, $stateParams) {
        $rootScope.group_id = $scope.session_id = $stateParams.session_id;


    }]);



//整个群组窗口控制器
app.controller('GroupsCtrl', [' ', '$scope', '$stateParams', function ($rootScope, $scope, $stateParams) {

    }]);


//整个群组窗口控制器
app.controller('GroupSessionCtrl', ['$rootScope', '$scope', '$stateParams', 'imService', function ($rootScope, $scope,$stateParams, imService) {
        $scope.session_id = $stateParams.session_id;
        $scope.group_name = 'aaaa';
        $scope.session_key = 'GROUP' + $scope.session_id;
                
        imService.select_session('GROUP', $scope.session_id);

        $scope.change_group_name = function(){
            $rootScope.groups[$scope.session_id].Name = $scope.group_name;
            $rootScope.$digest;
        };

        setTimeout(function () {
            //console.log($rootScope.groups);
            $('#group_session_wrapper').animate({scrollTop: 10000}, 500);
        }, 100);
    }]);


//群组聊天提交框的控制器
app.controller('GroupMessageFormCtrl', ['$scope', '$rootScope', '$stateParams', 'imService', function ($scope, $rootScope, $stateParams, imService) {
        $scope.message_input = '';

        $rootScope.message_form_upload_bar = 10;

        //joshua 聊天输入窗口上传图片
        $scope.upload_picture = function () {
            imService.upload_picture();
        };

        //joshua 聊天输入窗口上传文件
        $scope.upload_file = function () {
            imService.upload_file();
        };

        //joshua 聊天输入窗口 提交消息内容
        $scope.form_submit = function () {
            if ($scope.message_input === '') {
                //alert('总得说点什么吧');
            } else {

                imService.send_group_message($stateParams.session_id, $scope.message_input);
                setTimeout(function () {
                    $scope.message_input = '';
                    $('#message_form_input').val('');
                }, 100);
            }
        };

        //joshua 聊天输入窗口 enter 提交信息
        $scope.form_keyup = function ($event) {
            if ($event.keyCode == 13) {
                $scope.form_submit();
            }
        };




    }]);


//群组里单条信息里的，文本类型部分，对应 TIMTextElem
app.controller('GroupTIMTextElemCtrl', ['$scope', '$stateParams', function ($scope, $stateParams) {


    }]);
//群组里单条信息里的，地理类型部分，对应 TIMLocationElem
app.controller('GroupTIMLocationElemCtrl', ['$scope', '$stateParams', function ($scope, $stateParams) {


    }]);
//群组里单条信息里的，表情类型部分，对应 TIMFaceElem
app.controller('GroupTIMFaceElemCtrl', ['$scope', '$stateParams', function ($scope, $stateParams) {


    }]);
//群组里单条信息里的，自定义类型部分，对应 TIMCustomElem
app.controller('GroupTIMCustomElemCtrl', ['$scope', '$stateParams', function ($scope, $stateParams) {


    }]);
//群组里单条信息里的，声音类型部分，对应 TIMSoundElem
app.controller('GroupTIMSoundElemCtrl', ['$scope', '$stateParams', function ($scope, $stateParams) {


    }]);
//群组里单条信息里的，图片类型部分，对应 TIMImageElem
app.controller('GroupTIMImageElemCtrl', ['$scope', '$stateParams', function ($scope, $stateParams) {


    }]);
//群组里单条信息里的，文件类型部分，对应 TIMFileElem
app.controller('GroupTIMFileElemCtrl', ['$scope', '$stateParams', function ($scope, $stateParams) {


    }]);





app.controller('PersonalSessionCtrl', ['$scope', '$stateParams', function ($scope, $stateParams) {
        //alert(JSON.stringify($stateParams));
    }]);


app.controller('PersonalMessageFormCtrl', ['$scope', '$stateParams', function ($scope, $stateParams) {
        //alert(JSON.stringify($stateParams));
    }]);





