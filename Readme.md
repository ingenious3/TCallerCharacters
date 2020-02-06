# Readme

  - MVP Architecture
  - Retrofit and OkHttp for network api call
  - AsyncTask for processing response
 
# Flow 
  - There are three TextViews to for 10thCharacterResquest, Every10thCharacterRequest, CharacterCounts. Each TextView can show maximum 5 lines but scrollable property has been applied on these TextViews, so each TextView can be scrolled to show all content.
  - There is one button that will be used to initiate network call.
  - Retrofit will fetch the response from given endpoint & three AsyncTasks process this response in parallel & update the ui.

