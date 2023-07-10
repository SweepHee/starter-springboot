export default class Pagination {

    /*
    * @author SweepHee 전승희
    * */

    constructor(page=0, totalPage=0, totalElement=0,
                currentPage=0, isFirst=false,
                isLast=false, hasPrevious=false, hasNext=false, pageLength=10,
                pageRanges=this.calcPageRange())
    {
        this.page = Number(page);
        this.totalPage = totalPage;
        this.totalElement = totalElement;
        this.currentPage = currentPage;
        // this.currentElement = currentElement
        this.isFirst = isFirst;
        this.isLast = isLast;
        this.hasPrevious = hasPrevious;
        this.hasNext = hasNext;
        this.pageLength = pageLength;
        // this.size = size; // 필요가 없어서 주석처리
        this.pageRanges = pageRanges;
        this.paginationType = "ul";

        // pagination 영역
        this.area = document.getElementById("pagination");

        // true일때 0페이지부터 시작, false일때 1페이지부터 시작
        this.jpaPagination = true;

        this.buttonType = "button"; // button or a
        this.firstButton = "<<";
        this.previousButton = "<";
        this.lastButton = ">>";
        this.nextButton = ">";

        this.firstButtonTag = "";
        this.previousButtonTag = "";
        this.lastButtonTag = "";
        this.nextButtonTag = "";

        this.disableGo = false;
        this.customGo = null;
        this.renderAfter = null;
        this.customRenderBefore = null;

    }

    // jpa를 사용중일땐 true(기본값), 사용하지 않는다면 false
    setJpaPagination(jpaPagination) {
        this.jpaPagination = jpaPagination;
        return this;
    }
    
    // setPage만 해줘도 this.currentPage 세팅되게 수정
    setPage(page) {
        this.page = Number(page);
        this.currentPage = Number(page);
        return this;
    }

    setTotalPage(totalPage) {
        this.totalPage = Number(totalPage);
        return this;
    }

    setTotalElement(totalElement) {
        this.totalElement = totalElement;
        return this;
    }

    setCurrentPage(currentPage) {
        this.currentPage = Number(currentPage);
        this.page = Number(currentPage);
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
        return this;
    }

    setDisableGo(disableGo) {
        if (typeof disableGo != "boolean") {
            console.error(" 'disableGo' must be a boolean type ")
            return false;
        }
        this.disableGo = disableGo;
        return this;
    }
    
    // 페이지 이동 커스텀 함수 사용시 fnName에 문자열로 함수이름을 넘긴다. 넘기는 페이지에서 type="module"이 아닌 script태그에 함수가 정의되어 있어야 한다
    setCustomGo(fnName) {
        this.customGo = fnName;
        return this;
    }
    
    // 페이지네이션 렌더 전에 실행시키고 싶은 함수가 있을때 셋. 함수는 반드시 type="module"이 아닌 script태그에 정의되어야 함
    setCustomRenderBefore(customRenderBefore) {
        this.customRenderBefore = customRenderBefore;
        return this;
    }
    
    // 페이지네이션 렌더 후에 실행시키고 싶은 함수가 있을때 셋. 함수는 반드시 type="module"이 아닌 script태그에 정의되어야 함
    setRenderAfter(renderAfter) {
        this.renderAfter = renderAfter;
        return this;
    }

    // 첫페이지로 가기 버튼 만들기 디폴트 문자:<< (html 허용, 이미지 허용)
    setFirstButtonTag (firstButton, type = this.buttonType) {
        if (type == "button") {
            this.firstButtonTag = `<button type='button' class="page-common pages" onclick="${this.goFirst()}" data-page="${this.jpaPagination ? 0 : 1}">${firstButton}</button>`
        }
        else if (type == "a") {
            this.firstButtonTag = `<a href="javascript:void(0)" class="page-common pages" onclick="${this.goFirst()}" data-page="${this.jpaPagination ? 0 : 1}">${firstButton}</a>`
        }
    }
    // 이전 페이지로 가기 버튼 만들기 디폴트 문자:< (html 허용, 이미지 허용)
    setPreviousButtonTag (previousButton, type = this.buttonType) {
        if (type == "button") {
            this.previousButtonTag = `<button type='button' class="page-common pages" onclick="${this.goPrev()}" data-page="${Number(this.currentPage) - 1}">${previousButton}</button>`
        }
        else if (type == "a") {
            this.previousButtonTag = `<a href="javascript:void(0)" class="page-common pages" onclick="${this.goPrev()}" data-page="${Number(this.currentPage) - 1}">${previousButton}</a>`
        }
    }
    // 마지막 페이지로 가기 버튼 만들기 디폴트 문자:>> (html 허용, 이미지 허용)
    setLastButtonTag (lastButton, type = this.buttonType) {
        if (type == "button") {
            this.lastButtonTag = `<button type='button' class="page-common pages" onclick="${this.go(this.totalPage)}" data-page="${this.jpaPagination ? this.totalPage -1 : this.totalPage}">${lastButton}</button>`
        }
        else if (type == "a") {
            this.lastButtonTag = `<a href="javascript:void(0)" class="page-common pages" onclick="${this.go(this.totalPage)}" data-page="${this.jpaPagination ? this.totalPage -1 : this.totalPage}">${lastButton}</a>`
        }
    }
    // 다음 페이지로 가기 버튼 만들기 디폴트 문자:> (html 허용, 이미지 허용)
    setNextButtonTag (nextButton, type = this.buttonType) {
        if (type == "button") {
            this.nextButtonTag = `<button type='button' class="page-common pages" onclick="${this.goNext()}" data-page="${Number(this.currentPage) + 1}">${nextButton}</button>`
        }
        else if (type == "a") {
            this.nextButtonTag = `<a href="javascript:void(0)" class="page-common pages" onclick="${this.goNext()}" data-page="${Number(this.currentPage) + 1}">${nextButton}</a>`
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

        if (this.disableGo) {
            return "";
        }

        if (this.customGo) {
            return this.customGo + "()";
        }

        let queryString = "";
        if (this.jpaPagination) page--;

        if (this.currentPage == page) {
            return "";
        }

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

        page = Number(page);

        if (type == "button")
            return`<button class="page-button" type="button" data-page="${this.jpaPagination ? page-1 : page}" onclick="${this.go(page)}">${page}</button>`;
        else if (type == "a")
            return`<a href="javascript:void(0)" data-page="${this.jpaPagination ? page-1 : page}" class="page-button">${page}</a>`;
    }

    isCurrentPage (page) {
        let currentPage = this.jpaPagination ? this.currentPage+1 : this.currentPage;
        return page == currentPage;
    }

    renderBefore() {
        
        /* 재렌더링할때 펄스트, 프리비우스 다시 세팅 */
        if (
            (this.jpaPagination && this.currentPage == 0)
            || (!this.jpaPagination && this.currentPage == 1)
        ) {
            this.isFirst = true;
            this.hasPrevious = false;
        } else {
            this.isFirst = false;
            this.hasPrevious = true;
        }

        /* 재렌더링할때 라스트, 넥스트 다시 세팅 */
        if ( (this.jpaPagination && (this.totalPage-1) == this.currentPage)
            || (!this.jpaPagination && this.totalPage == this.currentPage)
        ) {
            this.isLast = true;
            this.hasNext = false;
        } else {
            this.isLast = false;
            this.hasNext = true;
        }


        this.firstButtonTag = "";
        this.previousButtonTag = "";
        this.lastButtonTag = "";
        this.nextButtonTag = "";

        this.setFirstButtonTag(this.firstButton)
        this.setPreviousButtonTag(this.previousButton)
        this.setLastButtonTag(this.lastButton)
        this.setNextButtonTag(this.nextButton)

        if (this.customRenderBefore) {
            eval(this.customRenderBefore + "()")
        }

    }

    render() {
        return this.paginationType === "ul" ?
            `
                <ul>
                ${this.isFirst ? "" : 
                `<li class='page-first pages' data-page='${this.jpaPagination ? 0 : 1}'> ${this.firstButtonTag} </li>`}
                ${this.hasPrevious ?
                `<li class='page-previous pages' data-page="${this.currentPage-1}"> ${this.previousButtonTag} </li>` : "" }
                ${this.pageRanges.map( (v) => 
                    `<li class="page-number pages ${this.isCurrentPage(v) ? 'current' : ''}" data-page="${v}">${this.setButtonToPageNumber(v)}</li>`
                ).join("")}
                ${this.hasNext ?
                `<li class="page-next pages" data-page="${this.currentPage+1}"> ${this.nextButtonTag} </li>` : "" }
                ${this.isLast ? "" : 
                `<li class="page-last pages" data-page="${this.jpaPagination ? this.totalPage -1 : this.totalPage}"> ${this.lastButtonTag} </li>` }
                </ul>        
                
            `
            : ""; // table타입 미구현
    }

    paginate() {
        this.renderBefore();
        this.area.innerHTML = "";
        let _render = this.render();
        this.area.insertAdjacentHTML("afterbegin", _render);

        if (this.renderAfter) {
            eval(this.renderAfter + "()");
        }


        return this;
    }

}