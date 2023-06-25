export default class Pagination {


    /*
    * HOW TO USE
    ** 생성자 매개변수를 모두 세팅하고 paginate() 메서드를 실행하면 페이지네이트 태그를 원하는 영역에 만들어준다.
    ** 기본적으로 id="pagination" 태그에 생성되나 setPaginationArea(document.getElementById("wantTAG"))로 원하는 영역을 설정할 수 있다
    ** 페이지는 ul > li > button 형태로 생성되고 button은 setButtonType(String "button" or "a") 로 a태그로 변경할 수 있다
    ** pageRanges는 배열로 보여질 페이지를 담는다 Ex) 현재 페이지가 5일때 3,4,5,6,7이 보여지고 싶다면 [3,4,5,6,7] 배열을 넣어주면 된다
    *** calcPageRange 메서드를 통해서 pageRanges를 생성해도 된다. pageLength는 필수로 설정되어야 한다.
    *** Ex) 현재 페이지가 5이고 pageLength가 2라면 calcPageRange()를 통해서 pageRange가 [3,4,5,6,7]이 생성됨
    * */

    constructor(page=0, totalPage=0, totalElement=0,
                currentPage=0, currentElement=0, isFirst=false,
                isLast=false, hasPrevious=false, hasNext=false, pageLength=10,
                size=10, pageRanges=this.calcPageRange())
    {
        this.page = page;
        this.totalPage = totalPage;
        this.totalElement = totalElement;
        this.currentPage = currentPage;
        this.currentElement = currentElement
        this.isFirst = isFirst;
        this.isLast = isLast;
        this.hasPrevious = hasPrevious;
        this.hasNext = hasNext;
        this.pageLength = pageLength; // 페이지네이션에 페이지번호가 최대 몇개까지 보이게 할 것인지
        this.size = size;
        this.pageRanges = pageRanges; // [1, 2, 3, 4...]
        this.paginationType = "ul"; // ul or table(미구현)

        // pagination 영역
        this.area = document.getElementById("pagination");

        // true일때 0페이지부터 시작, false일때 1페이지부터 시작
        this.jpaPagination = true;

        this.buttonType = "button"; // button or a
        this.firstButton = "<<";
        this.previousButton = "<";
        this.lastButton = ">>";
        this.nextButton = ">";

    }

    // jpa를 사용중일땐 true(기본값), 사용하지 않는다면 false
    setJpaPagination(jpaPagination) {
        this.jpaPagination = jpaPagination;
        return this;
    }

    setPage(page) {
        this.page = page;
        return this;
    }

    setTotalPage(totalPage) {
        this.totalPage = totalPage;
        return this;
    }

    setTotalElement(totalElement) {
        this.totalElement = totalElement;
        return this;
    }

    setCurrentPage(currentPage) {
        this.currentPage = currentPage;
        return this;
    }

    setCurrentElement(currentElement) {
        this.currentElement = currentElement;
        return this;
    }

    setIsFirst(isFirst) {
        this.isFirst = isFirst;
        return this;
    }

    setIsLast(isLast) {
        this.isLast = isLast;
        return this;
    }

    setHasPrevious(hasPrevious) {
        this.hasPrevious = hasPrevious;
        return this;
    }

    setHasNext(hasNext) {
        this.hasNext = hasNext;
        return this;
    }

    setPageLength(pageLength) {
        this.pageLength = pageLength;
        return this;
    }

    setSize(size) {
        this.size = size;
        return this;
    }

    setPageRanges(pageRanges) {
        this.pageRanges = pageRanges;
        return this;
    }


    // 페이지네이션 타입을 바꿉니다. ul or table(미구현)
    setPaginationType(type) {
        if (type != "ul" || type != "table")
            console.error("paginationType은 ul 혹은 table(미구현)만 설정 가능합니다")
        this.paginationType = type;
    }

    // 페이지 범위가 없을 경우 아래로 세팅한다
    calcPageRange() {

        let pageRange = [];
        const currentPage = this.jpaPagination ? this.currentPage+1 : this.currentPage;
        let pageLength = this.pageLength;
        pageRange.push(currentPage);
        while(pageLength > 0) {
            let prev = currentPage - pageLength;
            if (prev > 0) pageRange.push(prev);

            let next = currentPage + pageLength;
            if (next <= this.totalPage) pageRange.push(next);

            pageLength--;
        }
        pageRange.sort();
        this.pageRanges = pageRange;
        return this;

    }

    // 페이지네이션 영역의 태그를 넘겨줌. 반드시 document.getElementId querySelector 등으로 1개를 선택해서 넘겨줘야함
    setPaginationArea(area) {
        this.area = area;
        return this;
    }

    // 페이지 버튼 타입을 선택할 수 있음. button or a
    setButtonType(type = "button") {
        this.buttonType = type;
    }

    // 첫페이지로 가기 버튼 만들기 디폴트 문자:<< (html 허용, 이미지 허용)
    setFirstButton (firstButton, type = this.buttonType) {
        if (type == "button") {
            this.firstButton = `<button type='button' class="page-common pages" onclick="${this.goFirst()}">${firstButton}</button>`
        }
        else if (type == "a") {
            this.firstButton = `<a href="javascript:void(0)" class="page-common pages" onclick="${this.goFirst()}">${firstButton}</a>`
        }
    }
    // 이전 페이지로 가기 버튼 만들기 디폴트 문자:< (html 허용, 이미지 허용)
    setPreviousButton (previousButton, type = this.buttonType) {
        if (type == "button") {
            this.previousButton = `<button type='button' class="page-common pages" onclick="${this.goPrev()}">${previousButton}</button>`
        }
        else if (type == "a") {
            this.previousButton = `<a href="javascript:void(0)" class="page-common pages" onclick="${this.goPrev()}">${previousButton}</a>`
        }
    }
    // 마지막 페이지로 가기 버튼 만들기 디폴트 문자:>> (html 허용, 이미지 허용)
    setLastButton (lastButton, type = this.buttonType) {
        if (type == "button") {
            this.lastButton = `<button type='button' class="page-common pages" onclick="${this.go(this.totalPage)}">${lastButton}</button>`
        }
        else if (type == "a") {
            this.lastButton = `<a href="javascript:void(0)" class="page-common pages" onclick="${this.go(this.totalPage)}">${lastButton}</a>`
        }
    }
    // 다음 페이지로 가기 버튼 만들기 디폴트 문자:> (html 허용, 이미지 허용)
    setNextButton (nextButton, type = this.buttonType) {
        if (type == "button") {
            this.nextButton = `<button type='button' class="page-common pages" onclick="${this.goNext()}">${nextButton}</button>`
        }
        else if (type == "a") {
            this.nextButton = `<a href="javascript:void(0)" class="page-common pages" onclick="${this.goNext()}">${nextButton}</a>`
        }
    }

    goFirst() {
        return this.go(1);
    }

    goNext() {
        // go 함수에서 jpa의 경우 -1 시키기 때문에 제 자리에서 머무는 현상 발생. jpa +2, 일반 +1
        let page = this.jpaPagination ? this.currentPage + 2 : this.currentPage + 1;
        return this.go(page);
    }

    goPrev() {
        // go 함수에서 jpa의 경우 -1 시키기 때문에 2칸 이전으로 돌아가는 문제 발생
        let page = this.jpaPagination ? this.currentPage : this.currentPage - 1;
        return this.go(page);
    }




    go(page) {

        let queryString = "";
        if (this.jpaPagination) page--;
        window.location.search.replace(/[?&]+([^=&]+)=([^&]*)/gi,
            function (str, key, value) {
                if (key == "page") return;
                if (queryString != "") queryString += "&";
                queryString += `${key}=${value}`
            }
        );

        queryString += queryString == "" ? `page=${page}` : `&page=${page}`;
        return `location.href='${window.location.pathname + "?" + queryString}'`;

    }

    // 페이지 버튼 만들기
    setButtonToPageNumber(page, type = this.buttonType) {

        if (type == "button")
            return`<button class="page-button" type="button" data-page="${page}" onclick="${this.go(page)}">${page}</button>`;
        else if (type == "a")
            return`<a href="javascript:void(0)" data-page="${page}" class="page-button">${page}</a>`;
    }

    isCurrentPage (page) {
        let currentPage = this.jpaPagination ? this.currentPage+1 : this.currentPage;
        return page == currentPage;
    }

    renderBefore() {
        this.setFirstButton(this.firstButton)
        this.setPreviousButton(this.previousButton)
        this.setLastButton(this.lastButton)
        this.setNextButton(this.nextButton)
    }

    render() {
        return this.paginationType === "ul" ?
            `
                <ul>
                ${this.isFirst ? "" : 
                `<li class='page-first pages' data-page='${this.jpaPagination ? 0 : 1}'> ${this.firstButton} </li>`}
                ${this.hasPrevious ?
                `<li class='page-previous pages' data-page="${this.currentPage-1}"> ${this.previousButton} </li>` : "" }
                ${this.pageRanges.map( (v) => 
                    `<li class="page-number pages ${this.isCurrentPage(v) ? 'current' : ''}" data-page="${v}">${this.setButtonToPageNumber(v)}</li>`
                ).join("")}
                ${this.hasNext ?
                `<li class="page-next pages" data-page="${this.currentPage+1}"> ${this.nextButton} </li>` : "" }
                ${this.isLast ? "" : 
                `<li class="page-last pages" data-page="${this.totalPage}"> ${this.lastButton} </li>` }
                </ul>        
                
            `
            : ""; // table타입 미구현
    }

    paginate() {
        this.renderBefore();
        this.area.insertAdjacentHTML("afterbegin", this.render());
        return this;
    }

}