/*
 * 这是实体配置时为了重用模板而拼接请求路由的函数（只能处理不带_的前端路由）
 * @author  金杭
 * @param   {string} path 需要处理的路由
 * @return  userActs
 * */
app.service("routeCase",function(){
	return function (path) {
		
		var newarr,route,type,newarr1=[];
		
		route = path.split("/")[2];
		
		type = path.split("/")[3];
		
		newarr = type.toLowerCase().split(" ");
		
		for(var i = 0 ; i < newarr.length ; i++){
			
			newarr1.push(newarr[i][0].toUpperCase()+newarr[i].substring(1));
			
		}
		
		return route + newarr1.join(" ");
    }
})
/*
 * 根据不同路由取列表的资源，并添加PATCH请求
 * @author  金杭
 * @param   route 请求路由
 * @return  promise对象
 * */
.service("getListResource",
	[
		"$q",
		"$resource",
		function($q, $resource){
			return function(route) {
				
				var deferred = $q.defer();
                var promise  = deferred.promise;
                
				$resource("/rest/"+ route,{},{patch:{method:"PATCH"}}).get(function(resResult) {
					deferred.resolve(resResult);
				},function(){
					deferred.reject(resResult);
				});
				
				return promise;
			}
		}
	]
)
/*
 * 根据RestFul取单条的资源，并添加PATCH请求
 * @author  金杭
 * @param   data 修改或新增的数据
 * @return  promise对象
 * */
.service("getDetailResource",
	[	
		"$q",
		"$resource",
		function($q, $resource){
			return function(data){
				
				var deferred = $q.defer();
                var promise  = deferred.promise;
				
				var resource = $resource(data._links.self.href,{},{patch:{method:"PATCH"}}).get(function(resResult){
					//组装需要提交的值
					angular.forEach(data,function(v,k){
						resource[k] = v;
					});
					
					deferred.resolve(resource);
				},function(){
					deferred.reject(resResult);
				});
				
				return promise;
			}
		}
	]
)