insert into router_resources(pid,name,title,icon,hidden,level,
                             path,redirect,
                             componnet_url,order_no,keep_alive,
                             created_by,created_time,
                             last_updated_by,last_updated_time)
values(0,'Permission','Permission','lock',0,1,'/permission',
       '/permission/page','@/layout',101,0,'heshengfu',now(),
       'heshengfu',now());


insert into router_resources(pid,name,title,icon,hidden,level,
                             path,redirect,
                             componnet_url,order_no,keep_alive,
                             created_by,created_time,
                             last_updated_by,last_updated_time)
values(12,'PagePermission','Page Permission',null,0,2,'page',
       null,'@/views/permission/page',201,0,'heshengfu',now(),
       'heshengfu',now());

insert into router_resources(pid,name,title,icon,hidden,level,
                             path,redirect,
                             componnet_url,order_no,keep_alive,
                             created_by,created_time,
                             last_updated_by,last_updated_time)
values(12,'DirectivePermission','Directive Permission',null,0,2,'directive',
       null,'@/views/permission/directive',202,0,'heshengfu',now(),
       'heshengfu',now());

insert into router_resources(pid,name,title,icon,hidden,level,
                             path,redirect,
                             componnet_url,order_no,keep_alive,
                             created_by,created_time,
                             last_updated_by,last_updated_time)
values(12,'RolePermission','Role Permission',null,0,2,'role',
       null,'@/views/permission/role',203,0,'heshengfu',now(),
       'heshengfu',now());

insert into router_resources(pid,name,title,icon,hidden,level,
                             path,redirect,
                             componnet_url,order_no,keep_alive,
                             created_by,created_time,
                             last_updated_by,last_updated_time)
values(0,'icon','icon',null,0,1,'/icon',
       null,'@/layout',102,0,'heshengfu',now(),
       'heshengfu',now());

insert into router_resources(pid,name,title,icon,hidden,level,
                             path,redirect,
                             componnet_url,order_no,keep_alive,
                             created_by,created_time,
                             last_updated_by,last_updated_time)
values(16,'Icons','Icons','icon',0,1,'index',
       null,'@/views/icons/index',204,0,'heshengfu',now(),
       'heshengfu',now());

insert into router_resources(pid,name,title,icon,hidden,level,
                             path,redirect,
                             componnet_url,order_no,keep_alive,
                             created_by,created_time,
                             last_updated_by,last_updated_time)
values(0,'Example','Example','example',0,1,'/example','/example/list',
       '@/views/icons/index',103,0,'heshengfu',now(),
       'heshengfu',now());


insert into router_resources(pid,name,title,icon,hidden,level,
                             path,redirect,
                             componnet_url,order_no,keep_alive,
                             created_by,created_time,
                             last_updated_by,last_updated_time)
values(18,'CreateArticle','Create Article','edit',0,2,'create',null,
       '@/views/example/create',205,0,'heshengfu',now(),
       'heshengfu',now());


insert into router_resources(pid,name,title,icon,hidden,level,
                             path,redirect,
                             componnet_url,order_no,keep_alive,
                             created_by,created_time,
                             last_updated_by,last_updated_time)
values(18,'EditArticle','Edit Article','edit',0,2,'edit/:id(\\\\d+)',null,
       '@/views/example/edit',206,0,'heshengfu',now(),
       'heshengfu',now());

insert into router_resources(pid,name,title,icon,hidden,level,
                             path,redirect,
                             componnet_url,order_no,keep_alive,
                             created_by,created_time,
                             last_updated_by,last_updated_time)
values(18,'ArticleList','Article List','list',0,2,'list',null,
       '@/views/example/list',207,0,'heshengfu',now(),
       'heshengfu',now());

insert into router_resources(pid,name,title,icon,hidden,level,
                             path,redirect,
                             componnet_url,order_no,keep_alive,
                             created_by,created_time,
                             last_updated_by,last_updated_time)
values(0,'tab','tab',null,0,1,'/tab',null,
       '@/layout',104,0,'heshengfu',now(),
       'heshengfu',now());

insert into router_resources(pid,name,title,icon,hidden,level,
                             path,redirect,
                             componnet_url,order_no,keep_alive,
                             created_by,created_time,
                             last_updated_by,last_updated_time)
values(22,'Tab','Tab','tab',0,2,'index',null,
       '@/views/tab/index',208,0,'heshengfu',now(),
       'heshengfu',now());

insert into router_resources(pid,name,title,icon,hidden,level,
                             path,redirect,
                             componnet_url,order_no,keep_alive,
                             created_by,created_time,
                             last_updated_by,last_updated_time)
values(0,'ErrorPages','Error Pages','404',0,1,'/error','noRedirect',
       '@/layout',105,0,'heshengfu',now(),
       'heshengfu',now());

insert into router_resources(pid,name,title,icon,hidden,level,
                             path,redirect,
                             componnet_url,order_no,keep_alive,
                             created_by,created_time,
                             last_updated_by,last_updated_time)
values(24,'Page401','401',null,0,2,'401',null,
       '@/views/error-page/401',209,0,'heshengfu',now(),
       'heshengfu',now());

insert into router_resources(pid,name,title,icon,hidden,level,
                             path,redirect,
                             componnet_url,order_no,keep_alive,
                             created_by,created_time,
                             last_updated_by,last_updated_time)
values(24,'Page404','404',null,0,2,'404',null,
       '@/views/error-page/404',210,0,'heshengfu',now(),
       'heshengfu',now());

insert into router_resources(pid,name,title,icon,hidden,level,
                             path,redirect,
                             componnet_url,order_no,keep_alive,
                             created_by,created_time,
                             last_updated_by,last_updated_time)
values(0,'errorLog','error log',null,0,1,'/error-log',null,
       '@/layout',106,0,'heshengfu',now(),
       'heshengfu',now());

insert into router_resources(pid,name,title,icon,hidden,level,
                             path,redirect,
                             componnet_url,order_no,keep_alive,
                             created_by,created_time,
                             last_updated_by,last_updated_time)
values(27,'ErrorLog','Error Log','bug',0,2,'log',null,
       '@/views/error-log/index',211,0,'heshengfu',now(),
       'heshengfu',now());

insert into router_resources(pid,name,title,icon,hidden,level,
                             path,redirect,
                             componnet_url,order_no,keep_alive,
                             created_by,created_time,
                             last_updated_by,last_updated_time)
values(0,'Excel','Excel','excel',0,1,'/excel','/excel/export-excel',
       '@/layout',107,0,'heshengfu',now(),
       'heshengfu',now());

insert into router_resources(pid,name,title,icon,hidden,level,
                             path,redirect,
                             componnet_url,order_no,keep_alive,
                             created_by,created_time,
                             last_updated_by,last_updated_time)
values(29,'ExportExcel','Export Excel',null,0,2,'export-excel',null,
       '@/views/excel/export-excel',212,0,'heshengfu',now(),
       'heshengfu',now());

insert into router_resources(pid,name,title,icon,hidden,level,
                             path,redirect,
                             componnet_url,order_no,keep_alive,
                             created_by,created_time,
                             last_updated_by,last_updated_time)
values(29,'SelectExcel','Export Selected',null,0,2,'export-selected-excel',null,
       '@/views/excel/select-excel',213,0,'heshengfu',now(),
       'heshengfu',now());

insert into router_resources(pid,name,title,icon,hidden,level,
                             path,redirect,
                             componnet_url,order_no,keep_alive,
                             created_by,created_time,
                             last_updated_by,last_updated_time)
values(29,'MergeHeader','Merge Header',null,0,2,'export-merge-header',null,
       '@/views/excel/merge-header',214,0,'heshengfu',now(),
       'heshengfu',now());

insert into router_resources(pid,name,title,icon,hidden,level,
                             path,redirect,
                             componnet_url,order_no,keep_alive,
                             created_by,created_time,
                             last_updated_by,last_updated_time)
values(29,'UploadExcel','Upload Excel',null,0,2,'upload-excel',null,
       '@/views/excel/upload-excel',215,0,'heshengfu',now(),
       'heshengfu',now());


insert into router_resources(pid,name,title,icon,hidden,level,
                             path,redirect,
                             componnet_url,order_no,keep_alive,
                             created_by,created_time,
                             last_updated_by,last_updated_time)
values(0,'Zip','Zip','zip',0,1,'/zip','/zip/download',
       '@/layout',108,0,'heshengfu',now(),
       'heshengfu',now());

insert into router_resources(pid,name,title,icon,hidden,level,
                             path,redirect,
                             componnet_url,order_no,keep_alive,
                             created_by,created_time,
                             last_updated_by,last_updated_time)
values(34,'ExportZip','Export Zip',null,0,2,'download',null,
       '@/views/zip/index',216,0,'heshengfu',now(),
       'heshengfu',now());


insert into router_resources(pid,name,title,icon,hidden,level,
                             path,redirect,
                             componnet_url,order_no,keep_alive,
                             created_by,created_time,
                             last_updated_by,last_updated_time)
values(0,'PDF','PDF','pdf',0,1,'/pdf','/pdf/index',
       '@/layout',109,0,'heshengfu',now(),
       'heshengfu',now());

insert into router_resources(pid,name,title,icon,hidden,level,
                             path,redirect,
                             componnet_url,order_no,keep_alive,
                             created_by,created_time,
                             last_updated_by,last_updated_time)
values(36,'PDF','PDF','pdf',0,2,'index',null,
       '@/layout',217,0,'heshengfu',now(),
       'heshengfu',now());

insert into router_resources(pid,name,title,icon,hidden,level,
                             path,redirect,
                             componnet_url,order_no,keep_alive,
                             created_by,created_time,
                             last_updated_by,last_updated_time)
values(0,'downloadPdf','download',null,1,1,'/pdf/download',null,
       '@/views/pdf/download',110,0,'heshengfu',now(),
       'heshengfu',now());

insert into router_resources(pid,name,title,icon,hidden,level,
                             path,redirect,
                             componnet_url,order_no,keep_alive,
                             created_by,created_time,
                             last_updated_by,last_updated_time)
values(0,'theme','theme',null,0,1,'/theme',null,
       '@/layout',111,0,'heshengfu',now(),
       'heshengfu',now());

insert into router_resources(pid,name,title,icon,hidden,level,
                             path,redirect,
                             componnet_url,order_no,keep_alive,
                             created_by,created_time,
                             last_updated_by,last_updated_time)
values(39,'Theme','Theme','theme',0,2,'index',null,
       '@/views/theme/index',218,0,'heshengfu',now(),
       'heshengfu',now());

insert into router_resources(pid,name,title,icon,hidden,level,
                             path,redirect,
                             componnet_url,order_no,keep_alive,
                             created_by,created_time,
                             last_updated_by,last_updated_time)
values(0,'externalLink','external link',null,0,1,'external-link',null,
       '@/layout',112,0,'heshengfu',now(),
       'heshengfu',now());

insert into router_resources(pid,name,title,icon,hidden,level,
                             path,redirect,
                             componnet_url,order_no,keep_alive,
                             created_by,created_time,
                             last_updated_by,last_updated_time)
values(0,'notFound','Not Found',null,1,1,'*','/404',
       '@/views/error-page/404',199,0,'heshengfu',now(),
       'heshengfu',now());
