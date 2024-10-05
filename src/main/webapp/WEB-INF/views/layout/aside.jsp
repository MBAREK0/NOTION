<button data-drawer-target="default-sidebar" data-drawer-toggle="default-sidebar" aria-controls="default-sidebar" type="button" class="inline-flex items-center p-2 mt-2 ms-3 text-sm text-gray-500 rounded-lg sm:hidden hover:bg-gray-100 focus:outline-none focus:ring-2 focus:ring-gray-200 dark:text-gray-400 dark:hover:bg-gray-700 dark:focus:ring-gray-600">
    <span class="sr-only">Open sidebar</span>
    <svg class="w-6 h-6" aria-hidden="true" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg">
        <path clip-rule="evenodd" fill-rule="evenodd" d="M2 4.75A.75.75 0 012.75 4h14.5a.75.75 0 010 1.5H2.75A.75.75 0 012 4.75zm0 10.5a.75.75 0 01.75-.75h7.5a.75.75 0 010 1.5h-7.5a.75.75 0 01-.75-.75zM2 10a.75.75 0 01.75-.75h14.5a.75.75 0 010 1.5H2.75A.75.75 0 012 10z"></path>
    </svg>
</button>
<aside id="default-sidebar" class="fixed top-0 left-0 z-40 w-64 h-screen transition-transform -translate-x-full sm:translate-x-0" aria-label="Sidebar">
    <div class="h-full px-3 py-4 overflow-y-auto bg-gray-50 dark:bg-gray-800">
    <div class="h-10 flex items-center space-x-2 mb-10">
        <img src="https://imgs.search.brave.com/tcfDyp2j2sapuF-3VIXlgvywrihUODFU0soI_OHZjmo/rs:fit:860:0:0:0/g:ce/aHR0cHM6Ly9sb2dv/d2lrLmNvbS9jb250/ZW50L3VwbG9hZHMv/aW1hZ2VzL2dvb2ds/ZS10YXNrczcwNTIu/bG9nb3dpay5jb20u/d2VicA"
             alt="Logo"
             class="w-12 h-12 object-contain">
        <p class="text-xl font-semibold">Task Manager</p>
    </div>

        <ul class="space-y-2 font-medium mt-[100px] !important">

            <% if (request.getAttribute("role") != "manager") { %>
            <li>
                <a href="<%= request.getContextPath() %>/users" class="flex items-center p-2 text-gray-900 rounded-lg dark:text-white hover:bg-gray-100 dark:hover:bg-gray-700 group">
                    <svg class="flex-shrink-0 w-5 h-5 text-gray-500 transition duration-75 dark:text-gray-400 group-hover:text-gray-900 dark:group-hover:text-white" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="currentColor" viewBox="0 0 20 18">
                        <path d="M14 2a3.963 3.963 0 0 0-1.4.267 6.439 6.439 0 0 1-1.331 6.638A4 4 0 1 0 14 2Zm1 9h-1.264A6.957 6.957 0 0 1 15 15v2a2.97 2.97 0 0 1-.184 1H19a1 1 0 0 0 1-1v-1a5.006 5.006 0 0 0-5-5ZM6.5 9a4.5 4.5 0 1 0 0-9 4.5 4.5 0 0 0 0 9ZM8 10H5a5.006 5.006 0 0 0-5 5v2a1 1 0 0 0 1 1h11a1 1 0 0 0 1-1v-2a5.006 5.006 0 0 0-5-5Z"/>
                    </svg>
                    <span class="flex-1 ms-3 whitespace-nowrap">Users</span>
                </a>
            </li>
            <% } %>
            <li>
                <a href="#" class="flex items-center p-2 text-gray-900 rounded-lg dark:text-white hover:bg-gray-100 dark:hover:bg-gray-700 group">
                    <svg class="flex-shrink-0 w-5 h-5 text-gray-500 transition duration-75 dark:text-gray-400 group-hover:text-gray-900 dark:group-hover:text-white" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="currentColor" viewBox="-0.5 0 21 21">
                        <path opacity="0.4" d="M14.19 0.970215H5.81C2.17 0.970215 0 3.14022 0 6.78022V15.1602C0 18.8002 2.17 20.9702 5.81 20.9702H14.19C17.83 20.9702 20 18.8002 20 15.1602V6.78022C20 3.14022 17.83 0.970215 14.19 0.970215Z" fill="currentColor"/>
                        <path d="M16.3105 7.84033C16.3105 8.25033 15.9805 8.59033 15.5605 8.59033H10.3105C9.90055 8.59033 9.56055 8.25033 9.56055 7.84033C9.56055 7.43033 9.90055 7.09033 10.3105 7.09033H15.5605C15.9805 7.09033 16.3105 7.43033 16.3105 7.84033Z" fill="currentColor"/>
                        <path d="M7.97055 6.87027L5.72055 9.12027C5.57055 9.27027 5.38055 9.34027 5.19055 9.34027C5.00055 9.34027 4.80055 9.27027 4.66055 9.12027L3.91055 8.37027C3.61055 8.08027 3.61055 7.60027 3.91055 7.31027C4.20055 7.02027 4.67055 7.02027 4.97055 7.31027L5.19055 7.53027L6.91055 5.81027C7.20055 5.52027 7.67055 5.52027 7.97055 5.81027C8.26055 6.10027 8.26055 6.58027 7.97055 6.87027Z" fill="currentColor"/>
                        <path d="M16.3105 14.8403C16.3105 15.2503 15.9805 15.5903 15.5605 15.5903H10.3105C9.90055 15.5903 9.56055 15.2503 9.56055 14.8403C9.56055 14.4303 9.90055 14.0903 10.3105 14.0903H15.5605C15.9805 14.0903 16.3105 14.4303 16.3105 14.8403Z" fill="currentColor"/>
                        <path d="M7.97055 13.8703L5.72055 16.1203C5.57055 16.2703 5.38055 16.3403 5.19055 16.3403C5.00055 16.3403 4.80055 16.2703 4.66055 16.1203L3.91055 15.3703C3.61055 15.0803 3.61055 14.6003 3.91055 14.3103C4.20055 14.0203 4.67055 14.0203 4.97055 14.3103L5.19055 14.5303L6.91055 12.8103C7.20055 12.5203 7.67055 12.5203 7.97055 12.8103C8.26055 13.1003 8.26055 13.5803 7.97055 13.8703Z" fill="currentColor"/>
                    </svg>

                    <span class="flex-1 ms-3 whitespace-nowrap">Tasks</span>
                </a>
            </li>
            <li>
                <a href="#" class="flex items-center p-2 text-gray-900 rounded-lg dark:text-white hover:bg-gray-100 dark:hover:bg-gray-700 group">
                    <svg class="flex-shrink-0 w-5 h-5 text-gray-500 transition duration-75 dark:text-gray-400 group-hover:text-gray-900 dark:group-hover:text-white" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="currentColor" viewBox="0 0 16 16">
                        <path fill-rule="evenodd" clip-rule="evenodd" d="M16 8L8 0H0V8L8 16L16 8ZM4.5 6C5.32843 6 6 5.32843 6 4.5C6 3.67157 5.32843 3 4.5 3C3.67157 3 3 3.67157 3 4.5C3 5.32843 3.67157 6 4.5 6Z" fill="currentColor"/>
                    </svg>


                    <span class="flex-1 ms-3 whitespace-nowrap">Tags</span>
                </a>
            </li>

            <li>
                <a href="<%= request.getContextPath() %>/?action=logout" class="flex items-center p-2 text-gray-900 rounded-lg dark:text-white hover:bg-gray-100 dark:hover:bg-gray-700 group">
                    <svg class="flex-shrink-0 w-5 h-5 text-gray-500 transition duration-75 dark:text-gray-400 group-hover:text-gray-900 dark:group-hover:text-white" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 18 16">
                        <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M17 8h-11m0 0l3.5-4m-3.5 4l3.5 4m-3.5-11h-3a2 2 0 0 0-2 2v10a2 2 0 0 0 2 2h3"/>
                    </svg>


                    <span class="flex-1 ms-3 whitespace-nowrap">Logout</span>
                </a>
            </li>
        </ul>
    </div>
</aside>
