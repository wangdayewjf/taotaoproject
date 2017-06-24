这时候包含的模板

<#list lm as l >
	<#list l?keys as k>
		${l[k].id}
		${l[k].name}
	</#list>
</#list>