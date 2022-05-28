# RoadToLeBonCoin


## Architecture Overview:
The project architecture is based on google architecture.

![image](https://user-images.githubusercontent.com/2206036/170807103-2c0c9499-c437-4dad-9fbf-964c0fe52609.png)

1. **UI Layer** This part is responsible for displaying the data provided by the ViewModel. It contains a simple RecyclerView with It’s Adapter.
2. **Presentation** Relies on the Android Architecture View Model Component that Handles The persistence of data when the configuration of the device changes ,means we will still have the elements we already fetched and the pages we already loaded.

5. **Data** Repository is the layer handling the data either from the network (Retrofit) or locally (Room).

**LiveData** is used in the **Presentation** And User Interface layer and  coroutines is used beneath **Presentation**.

## Libraries : 
* Coroutines
* Hilt 
* Retrofit 
* Glide
* Gson
* Android Support Library
* Android Architecture Components 
	* LiveData
	* ViewModel
* Room 

## Tested layers :
 - [x] Repository
 - [x] Presentation
- [ ] User Interface 

## What can be imporoved :
 - Add domaine layer.
 - User interface.
 - Add more unit test.
 - Add paging library.
