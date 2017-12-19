LoadRecyclerView [ ![Download](https://api.bintray.com/packages/kornan/maven/LoadRecyclerView/images/download.svg) ](https://bintray.com/kornan/maven/LoadRecyclerView/_latestVersion)
===============
This is a RecyclerView tool 


Usage
---
- Gradle

```
compile 'com.flobberworm:LoadRecyclerView:latest.integration'
```
- Maven
```
<dependency>
  <groupId>com.flobberworm</groupId>
  <artifactId>LoadRecyclerView</artifactId>
  <version>0.2.0</version>
  <type>pom</type>
</dependency>
```
- Sample Code
```
RecyclerManager recyclerManager = new RecyclerManager(recyclerView);
recyclerManager.setLoadStatusAdapter(adapter);//adapter is Custom Adapter
recyclerManager.setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                
            }
        });
        
recyclerManager.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                        
            }
        });

```
License
---

Copyright 2017 flobberworm

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0 
     
Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
