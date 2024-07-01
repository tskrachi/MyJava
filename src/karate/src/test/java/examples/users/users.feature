Feature: sample karate test script
  for help, see: https://github.com/karatelabs/karate/wiki/IDE-Support

  Background:
    * url 'http://localhost:8080/demo-0.0.1-SNAPSHOT-plain/'
#    * url 'http://localhost:8080/demo/'
	* def searchKey = 
	"""
	function(ary, key)
	{
		for (const item of ary) {
			if (item.fileName == key) return item.fileSize;
		}
		return -1
	}
	"""
  Scenario: GETのテスト
  	* def reqHeader = { API-Key : 'aaaaaa' }
    Given path 'api/get'
    And headers reqHeader
    When method get
    Then status 200
	* print response
	* match response == 'aaaaaa'
  
  Scenario: UPLOADのテスト
    * def fi = 
    """
    	{
    		type: "aaaa",
    		name: "bbbb"
    	}
   	"""
    Given path 'api/upload'
    And multipart field fileinfo = fi
	And multipart file filedata = { read: 'image00.JPG', filename: 'image00.JPG', contentType: 'image/jpeg' }
    When method post
    Then status 200
    * match response == {"fileName": "bbbb", "fileSize": 50834}
	* print response

  Scenario: 404のテスト
    Given path 'api/notfound'
    When method get
    Then status 404
 	
   Scenario: 400のテスト
    Given path 'api/error'
    When method get
    Then status 400
 
    Scenario: ファイルダウンロード
    Given path 'api/download'
    When method get
    Then status 200
 
     Scenario: JSON配列
    Given path 'api/getlist'
    When method get
    Then status 200
 	* print response
 	* def filesize = searchKey(response, "image02")
 	* print filesize
 	* def filebefore = 5677
 	* match filesize == filebefore + 1